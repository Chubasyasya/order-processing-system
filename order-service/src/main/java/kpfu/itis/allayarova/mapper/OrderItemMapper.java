package kpfu.itis.allayarova.mapper;

import kpfu.itis.allayarova.data.model.OrderItemEntity;
import kpfu.itis.allayarova.data.view.OrderItemView;
import kpfu.itis.allayarova.data.view.ProductView;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class OrderItemMapper{
    public OrderItemMapper() {
    }

    public OrderItemView toView(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        } else {
            OrderItemView.OrderItemViewBuilder orderItemView = OrderItemView.builder();
            orderItemView.product(this.map(entity.getProductId()));
            orderItemView.id(entity.getId());
            orderItemView.productName(entity.getProductName());
            orderItemView.quantity(entity.getQuantity());
            orderItemView.price(entity.getPrice());
            orderItemView.order(entity.getOrder());
            return orderItemView.build();
        }
    }

    private ProductView map(Long id) {
        if (id == null) {
            return null;
        } else {
            ProductView product = new ProductView();
            product.setId(id);
            return product;
        }
    }

    public Set<OrderItemView> toViews(Set<OrderItemEntity> entities) {
        if (entities == null) {
            return null;
        } else {
            Set<OrderItemView> set = new LinkedHashSet(Math.max((int)((float)entities.size() / 0.75F) + 1, 16));
            Iterator var3 = entities.iterator();

            while(var3.hasNext()) {
                OrderItemEntity orderItemEntity = (OrderItemEntity)var3.next();
                set.add(this.toView(orderItemEntity));
            }

            return set;
        }


    }
}