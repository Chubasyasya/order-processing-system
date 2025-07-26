package kpfu.itis.allayarova.mapper;

import kpfu.itis.allayarova.model.ProductEntity;
import kpfu.itis.allayarova.view.ProductView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductView mapToView(ProductEntity productEntity);
    List<ProductView> mapToViewList(List<ProductEntity> productEntities);
}
