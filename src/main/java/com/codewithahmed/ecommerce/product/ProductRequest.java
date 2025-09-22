package com.codewithahmed.ecommerce.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int stock;
    private Long categoryId;
}
