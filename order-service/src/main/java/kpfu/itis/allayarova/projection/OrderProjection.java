package kpfu.itis.allayarova.projection;

import kpfu.itis.allayarova.data.model.OrderEntity;
import kpfu.itis.allayarova.data.model.OrderStatus;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import kpfu.itis.allayarova.exception.OrderNotFoundException;
import kpfu.itis.allayarova.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

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
        order.setItems(event.getItems());

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event){
        OrderEntity order = orderRepository.findById(event.getId()).orElseThrow(() -> new OrderNotFoundException(event.getId()));
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);
    }
}
