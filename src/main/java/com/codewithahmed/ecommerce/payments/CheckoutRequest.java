package com.codewithahmed.ecommerce.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotNull(message = "Cart Id is required")
    private Long cartId;
}
