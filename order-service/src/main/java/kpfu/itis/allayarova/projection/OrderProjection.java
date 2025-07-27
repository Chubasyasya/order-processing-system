package kpfu.itis.allayarova.projection;

import kpfu.itis.allayarova.data.model.OrderEntity;
import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.data.model.OrderStatus;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import kpfu.itis.allayarova.exception.OrderNotFoundException;
import kpfu.itis.allayarova.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProjection {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();
        order.setId(event.getId());
        order.setCustomerId(event.getCustomerId());
        order.setOrderDate(event.getOrderDate());

        Set<OrderItemEntity> items = event.getItems().stream().map(item -> {
            OrderItemEntity entity = new OrderItemEntity();
            entity.setId(item.getId());
            entity.setProductId(item.getProductId());
            entity.setQuantity(item.getQuantity());
            entity.setOrder(order);
            return entity;
        }).collect(Collectors.toSet());

        order.setItems(items);

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event){
        OrderEntity order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new OrderNotFoundException(event.getOrderId()));
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);
    }
}
