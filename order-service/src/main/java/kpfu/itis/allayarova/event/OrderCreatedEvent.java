package kpfu.itis.allayarova.event;

import kpfu.itis.allayarova.data.model.OrderItemEntity;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class OrderCreatedEvent {
    Long id;
    Long customerId;
    LocalDateTime orderDate;
    Set<OrderItemEntity> items;
}