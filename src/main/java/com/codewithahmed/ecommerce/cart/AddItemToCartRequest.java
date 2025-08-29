package com.codewithahmed.ecommerce.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddItemToCartRequest {

    @NotNull
    private Long productId;
    private int quantity = 1;
}
