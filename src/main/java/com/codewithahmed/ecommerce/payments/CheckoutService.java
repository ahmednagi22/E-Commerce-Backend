package com.codewithahmed.ecommerce.payments;

import com.codewithahmed.ecommerce.auth.AuthService;
import com.codewithahmed.ecommerce.cart.CartRepository;
import com.codewithahmed.ecommerce.cart.CartService;
import com.codewithahmed.ecommerce.common.exception.ResourceNotFoundException;
import com.codewithahmed.ecommerce.order.Order;
import com.codewithahmed.ecommerce.order.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart with id " + request.getCartId() + " not found");
        }
        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("cart is empty");
        }
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handelWebHook(WebHookRequest request) throws SignatureVerificationException {
        paymentGateway.parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                            var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                            order.setPaymentStatus(paymentResult.getPaymentStatus());
                            orderRepository.save(order);
                        }
                );

    }
}
