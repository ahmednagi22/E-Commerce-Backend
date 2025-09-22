package com.codewithahmed.ecommerce.product;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private int stock;

    private Long sellerId;
    private String sellerName;


    private Long categoryId;
    private String categoryName;

}
