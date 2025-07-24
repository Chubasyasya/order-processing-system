package kpfu.itis.allayarova.mapper;

import kpfu.itis.allayarova.data.model.OrderEntity;
import kpfu.itis.allayarova.data.view.OrderView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderView toView(OrderEntity entity);
}
