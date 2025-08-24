package com.codewithahmed.ecommerce.cart;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 100, message = "Quantity must be less than 100")
    private Integer quantity;
}
