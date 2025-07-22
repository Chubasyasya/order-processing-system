package kpfu.itis.allayarova.dto.request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderItem {
    Long productId;
    String productName;
    Integer quantity;
    BigDecimal price;
}
