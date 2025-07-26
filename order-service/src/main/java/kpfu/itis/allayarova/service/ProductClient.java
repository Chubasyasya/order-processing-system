package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.data.view.ProductView;
import kpfu.itis.allayarova.data.view.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductClient {
    private final String PRODUCTS_URL = "http://inventory-service/api/v1/products?ids={ids}&fields={fields}";
    private final RestTemplate restTemplate;
    public List<ProductView> getProductsByIdsAndFields(List<Long> ids, Set<String> fields) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String fieldsParam = String.join(",", fields);

        Products productsWrapper = restTemplate.getForObject(
                PRODUCTS_URL,
                Products.class,
                idsParam,
                fieldsParam);

        return productsWrapper.getProducts();
    }
}
