package kpfu.itis.allayarova.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import kpfu.itis.allayarova.data.view.OrderItemView;
import kpfu.itis.allayarova.data.view.OrderView;
import kpfu.itis.allayarova.data.view.ProductView;
import kpfu.itis.allayarova.query.OrderQuery;
import kpfu.itis.allayarova.service.OrderQueryHandler;
import kpfu.itis.allayarova.service.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderQueryResolver implements GraphQLQueryResolver {
    private final OrderQueryHandler orderQueryHandler;
    private final ProductClient productClient;

    public OrderView getOrder(Long id, DataFetchingEnvironment env) {
        OrderView orderView = orderQueryHandler.handle(new OrderQuery(id));
        List<Long> ids = orderView.getItems().stream()
                .map(OrderItemView::getProduct)
                .map(ProductView::getId)
                .collect(Collectors.toList());

        Set<String> productFields = env.getSelectionSet()
                .getFields("items/product/*").stream()
                .map(field -> field.getName())
                .collect(Collectors.toSet());

        List<ProductView> products = productClient.getProductsByIdsAndFields(ids, productFields);

        Map<Long, ProductView> productViewMap = products.stream()
                .collect(Collectors.toMap(ProductView::getId, p -> p));

        orderView.getItems().forEach(item -> {
            Long productId = item.getProduct().getId();
            item.setProduct(productViewMap.get(productId));
        });

        return orderView;
    }

}
