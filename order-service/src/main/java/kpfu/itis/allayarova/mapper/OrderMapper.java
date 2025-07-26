package kpfu.itis.allayarova.mapper;

import kpfu.itis.allayarova.data.model.OrderEntity;
import kpfu.itis.allayarova.data.view.OrderView;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderMapper() {
    }

    public OrderView toView(OrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            OrderView.OrderViewBuilder orderView = OrderView.builder();
            orderView.id(entity.getId());
            orderView.customerId(entity.getCustomerId());
            orderView.orderDate(entity.getOrderDate());
            orderView.status(entity.getStatus());
            orderView.items(this.orderItemMapper.toViews(entity.getItems()));
            orderView.version(entity.getVersion());
            return orderView.build();
        }
    }
}
