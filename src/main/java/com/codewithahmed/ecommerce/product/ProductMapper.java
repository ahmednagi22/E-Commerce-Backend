package com.codewithahmed.ecommerce.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "seller.name", target = "sellerName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest, @MappingTarget Product product);
}
