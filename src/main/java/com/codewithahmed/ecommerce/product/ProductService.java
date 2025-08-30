package com.codewithahmed.ecommerce.product;

import com.codewithahmed.ecommerce.category.Category;
import com.codewithahmed.ecommerce.category.CategoryRepository;
import com.codewithahmed.ecommerce.common.exception.ResourceNotFoundException;
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
        if (product.isPresent()) {
            return productMapper.toProductDto(product.get());
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }

    public ProductDto createProduct(ProductDto productDto) {
        // get category by id
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (category.isPresent()) {
            Product product = productMapper.toProduct(productDto);
            product.setCategory(category.get());
            productRepository.save(product);
            productDto.setId(product.getId());
            return productDto;
        } else {
            throw new ResourceNotFoundException("Category with id " + productDto.getCategoryId() + " not found.");
        }
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category with id " + productDto.getCategoryId() + " not found.")
        );

        Product product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + productDto.getId() + " not found.")
        );

        product.setCategory(category);
        productMapper.updateProduct(productDto, product);
        Product updated = productRepository.save(product);
        return productMapper.toProductDto(updated);

    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id + " not found.")
        );
        productRepository.delete(product);

    }
}

