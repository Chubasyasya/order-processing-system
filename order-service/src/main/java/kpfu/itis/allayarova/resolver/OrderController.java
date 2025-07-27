package kpfu.itis.allayarova.resolver;

import kpfu.itis.allayarova.command.CreateOrderCommand;
import kpfu.itis.allayarova.data.view.OrderItemView;
import kpfu.itis.allayarova.data.view.OrderView;
import kpfu.itis.allayarova.data.view.ProductView;
import kpfu.itis.allayarova.input.OrderInput;
import kpfu.itis.allayarova.mapper.OrderItemMapper;
import kpfu.itis.allayarova.query.OrderQuery;
import kpfu.itis.allayarova.service.OrderQueryHandler;
import kpfu.itis.allayarova.service.OrderStatusPublisher;
import kpfu.itis.allayarova.service.ProductClient;
import kpfu.itis.allayarova.util.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderQueryHandler orderQueryHandler;
    private final ProductClient productClient;
    private final CommandGateway commandGateway;
    private final OrderItemMapper orderItemMapper;
    private final OrderStatusPublisher orderStatusPublisher;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);

    @QueryMapping
    public OrderView getOrder(@Argument Long id) {
        OrderView orderView = orderQueryHandler.handle(new OrderQuery(id));

        List<Long> ids = orderView.getItems().stream()
                .map(OrderItemView::getProduct)
                .map(ProductView::getId)
                .collect(Collectors.toList());

        List<ProductView> products = productClient.getProductsByIdsAndFields(ids, Set.of("id", "name", "price"));

        Map<Long, ProductView> productViewMap = products.stream()
                .collect(Collectors.toMap(ProductView::getId, p -> p));

        orderView.getItems().forEach(item -> {
            Long productId = item.getProduct().getId();
            item.setProduct(productViewMap.get(productId));
        });

        return orderView;
    }

    @MutationMapping
    public void publishOrder(@Argument OrderInput input) {
        Long orderId = idGenerator.nextId();

        CreateOrderCommand command = new CreateOrderCommand(orderId, input.getCustomerId(), input.getItems().stream().map(orderItemMapper::toEntity).collect(Collectors.toSet()));
        commandGateway.send(command);
    }

    @SubscriptionMapping
    public Flux<OrderView> orderStatusChanged(@Argument Long orderId) {
        return orderStatusPublisher.subscribeToOrder(orderId);
    }
}
