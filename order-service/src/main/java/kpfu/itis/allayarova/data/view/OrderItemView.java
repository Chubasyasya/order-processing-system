package kpfu.itis.allayarova.data.view;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kpfu.itis.allayarova.data.model.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemView {
    private Long id;
    private ProductView product;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private OrderEntity order;
}
