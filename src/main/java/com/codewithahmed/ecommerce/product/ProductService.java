package com.codewithahmed.ecommerce.product;

import com.codewithahmed.ecommerce.common.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Long categoryId) {
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId)
                    .stream()
                    .map(productMapper::toProductDto)
                    .toList();
        } else {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toProductDto)
                    .toList();
        }
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productMapper.toProductDto(product.get());
        }
        else{
            throw new ProductNotFoundException("Product with id "+ id +" not found.");
        }
    }
}
