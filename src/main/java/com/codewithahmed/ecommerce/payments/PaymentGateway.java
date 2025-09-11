package com.codewithahmed.ecommerce.payments;

import com.codewithahmed.ecommerce.order.Order;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);

}
