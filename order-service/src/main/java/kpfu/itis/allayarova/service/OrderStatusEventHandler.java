package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.data.model.OrderStatus;
import kpfu.itis.allayarova.data.view.OrderView;
import kpfu.itis.allayarova.event.OrderCompletedEvent;
import kpfu.itis.allayarova.event.OrderCreatedEvent;
import kpfu.itis.allayarova.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusEventHandler {

    private final OrderStatusPublisher orderStatusPublisher;
    private final OrderMapper orderMapper;


    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderView orderView = new OrderView();
        orderView.setId(event.getId());
        orderView.setCustomerId(event.getCustomerId());
        orderView.setStatus(OrderStatus.CREATED);
        orderStatusPublisher.publishStatus(orderView);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        OrderView orderView = new OrderView();
        orderView.setId(event.getOrderId());
        orderView.setStatus(OrderStatus.PROCESSING);
        orderStatusPublisher.publishStatus(orderView);
    }
}