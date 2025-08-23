package com.codewithahmed.ecommerce.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartProductDto {
    private long id;
    private String name;
    private BigDecimal price;

}
