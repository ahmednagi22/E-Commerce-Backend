package com.codewithahmed.ecommerce.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        return ResponseEntity.ok(productService.getAllProducts(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        ProductResponse createdProduct = productService.createProduct(productRequest);
        var uri = uriComponentsBuilder.path("/api/v1/products/{id}").buildAndExpand(createdProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(createdProduct);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long id,
//                                                        @RequestBody ProductRequest productRequest) {
//        productRequest.setId(id);
//        return ResponseEntity.ok(productService.updateProduct(productRequest));
//    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
