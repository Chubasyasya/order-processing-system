package kpfu.itis.allayarova.service;

import kpfu.itis.allayarova.mapper.ProductMapper;
import kpfu.itis.allayarova.repository.ProductRepository;
import kpfu.itis.allayarova.view.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryHandler {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Products getProducts(List<Long> ids){
        return new Products(productMapper.mapToViewList(productRepository.findAllById(ids)));
    }
}
