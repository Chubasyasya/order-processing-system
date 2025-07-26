package kpfu.itis.allayarova.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    private Long id;

    @Column(name = "product_id", nullable = true)
    private Long productId;

    @Column(name = "product_name", nullable = true)
    private String productName;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "price", precision = 12, scale = 2, nullable = true)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private OrderEntity order;

    @Transient
    public BigDecimal getTotalPrice() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}