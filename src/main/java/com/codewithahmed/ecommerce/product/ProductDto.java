package com.codewithahmed.ecommerce.product;

import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int stock;
    private Long categoryId;
}
