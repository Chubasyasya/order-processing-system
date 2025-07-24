package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.data.view.ProductView;
import kpfu.itis.allayarova.data.view.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.api.AnnotationsProto.http;

@RequiredArgsConstructor
@Component
public class ProductClient {
    private final RestTemplate restTemplate;
    public List<ProductView> getProductsByIdsAndFields(List<Long> ids, Set<String> fields) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String fieldsParam = String.join(",", fields);

        String url = "http://inventory-service/api/v1/products?ids={ids}&fields={fields}";

        Products productsWrapper = restTemplate.getForObject(
                url,
                Products.class,
                idsParam,
                fieldsParam);

        return productsWrapper.getProducts();
    }
}
