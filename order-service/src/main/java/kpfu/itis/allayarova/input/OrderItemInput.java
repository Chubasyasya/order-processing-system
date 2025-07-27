package kpfu.itis.allayarova.input;

import lombok.Data;

@Data
public class OrderItemInput {
    private Integer quantity;
    private Long productId;
}
