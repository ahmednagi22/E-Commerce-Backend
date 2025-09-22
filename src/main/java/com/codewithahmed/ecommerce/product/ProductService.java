package com.codewithahmed.ecommerce.product;

import com.codewithahmed.ecommerce.auth.AuthService;
import com.codewithahmed.ecommerce.category.CategoryNotFoundException;
import com.codewithahmed.ecommerce.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final AuthService authService;

    public List<ProductResponse> getAllProducts(Long categoryId) {
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId)
                    .stream()
                    .map(productMapper::toProductResponse)
                    .toList();
        } else {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toProductResponse)
                    .toList();
        }
    }

    public ProductResponse getProductById(Long id) {
        var product = productRepository.findById(id);
        if (product.isPresent()) {
            return productMapper.toProductResponse(product.get());
        } else {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
    }
//
    public ProductResponse createProduct(ProductRequest productRequest) {
        // get category by id
        var category = categoryRepository.findById(productRequest.getCategoryId());
        if (category.isPresent()) {
            Product product = productMapper.toProduct(productRequest);
            product.setCategory(category.get());
            product.setSeller(authService.getCurrentUser());
            productRepository.save(product);
            return productMapper.toProductResponse(product);
        } else {
            throw new CategoryNotFoundException("Category with id " + productRequest.getCategoryId() + " not found.");
        }
    }
//
//    public ProductRequest updateProduct(ProductRequest productRequest) {
//        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
//                () -> new ResourceNotFoundException("Category with id " + productRequest.getCategoryId() + " not found.")
//        );
//
//        Product product = productRepository.findById(productRequest.getId()).orElseThrow(
//                () -> new ResourceNotFoundException("Product with id " + productRequest.getId() + " not found.")
//        );
//
//        product.setCategory(category);
//        productMapper.updateProduct(productRequest, product);
//        Product updated = productRepository.save(product);
//        return productMapper.toProductDto(updated);
//
//    }
//
//    public void deleteProduct(Long id) {
//        Product product = productRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Product with id " + id + " not found.")
//        );
//        productRepository.delete(product);
//
//    }
}

