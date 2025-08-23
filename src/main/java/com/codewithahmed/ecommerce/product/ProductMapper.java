package com.codewithahmed.ecommerce.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toProductDto(Product product);
    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductDto productDto);

    void updateProduct(ProductDto productDto, @MappingTarget Product product);
}
