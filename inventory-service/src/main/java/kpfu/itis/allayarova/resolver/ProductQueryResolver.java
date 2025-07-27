package kpfu.itis.allayarova.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import kpfu.itis.allayarova.service.ProductQueryHandler;
import kpfu.itis.allayarova.view.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryResolver implements GraphQLQueryResolver {
    private final ProductQueryHandler productQueryHandler;
    public Products getProducts(List<Long> ids){
        return productQueryHandler.getProducts(ids);
    }
}
