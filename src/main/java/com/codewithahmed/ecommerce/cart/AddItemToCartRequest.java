package com.codewithahmed.ecommerce.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemToCartRequest {

    @NotNull
    private long productId;

    private int quantity = 1;
}
