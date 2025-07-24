package kpfu.itis.allayarova.data.view;

import kpfu.itis.allayarova.service.ProductClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Products {
    List<ProductView> products;
}
