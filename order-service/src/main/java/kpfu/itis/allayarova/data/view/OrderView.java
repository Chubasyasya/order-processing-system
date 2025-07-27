package kpfu.itis.allayarova.data.view;

import kpfu.itis.allayarova.data.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderView {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Set<OrderItemView> items;
    private Long version;
}
