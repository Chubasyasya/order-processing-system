package kpfu.itis.allayarova.event;

import kpfu.itis.allayarova.dto.request.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public class OrderCreatedEvent {
    Long id;
    Long customerId;
    LocalDateTime orderDate;
    Set<OrderItem> items;
}