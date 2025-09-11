package com.codewithahmed.ecommerce.payments;

import com.codewithahmed.ecommerce.auth.AuthService;
import com.codewithahmed.ecommerce.cart.CartRepository;
import com.codewithahmed.ecommerce.cart.CartService;
import com.codewithahmed.ecommerce.common.exception.ResourceNotFoundException;
import com.codewithahmed.ecommerce.order.Order;
import com.codewithahmed.ecommerce.order.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws StripeException {
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
            //create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success.html?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel.html");
            order.getOrderItems().forEach(item -> {
                var lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity((long) item.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmountDecimal(
                                                item.getPrice_At_Order()
                                                    .multiply(BigDecimal.valueOf(100)))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .setDescription(item.getProduct().getDescription())
                                                        .build()
                                        ).build()
                        )
                        .build();
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getUrl());
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            orderRepository.delete(order);
            throw e ;
        }
    }
}
