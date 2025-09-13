package com.codewithahmed.ecommerce.payments;

import com.codewithahmed.ecommerce.order.Order;

import java.util.Optional;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebHookRequest request);

}
