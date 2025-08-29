package com.codewithahmed.ecommerce.cart;

import com.codewithahmed.ecommerce.common.exception.CartNotFoundException;
import com.codewithahmed.ecommerce.common.exception.ProductNotFoundException;
import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto addItemToCart(Long cartId, AddItemToCartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(
                        () -> new ProductNotFoundException("Product with id " + request.getProductId() + " not found.")
                );

        Cart cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(
                () -> new CartNotFoundException("Cart with id " + cartId + " not found.")
        );


        CartItem cartItem = cart.addItem(product, request.getQuantity());

        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem);
    }

    public CartDto getCartById(Long cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(
                () -> new CartNotFoundException("Cart with id " + cartId + " not found.")
        );
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto updateCartItem(Long cartId,
                                      Long productId,
                                      UpdateCartItemRequest request) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(
                () -> new CartNotFoundException("Cart with id " + cartId + " not found.")
        );
        CartItem cartItem = cart.getItem(productId);
        if (cartItem != null) {
            cartItem.setQuantity(request.getQuantity());
            cartRepository.save(cart);
            return cartMapper.toCartItemDto(cartItem);
        } else {
            return null;
        }
    }

    public void removeItem(Long cartId, Long productId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(
                ()->new CartNotFoundException("Cart with id " + cartId + " not found."));

        cart.removeItem(productId);
        cartRepository.save(cart);


    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(
                ()->new CartNotFoundException("Cart with id " + cartId + " not found."));
        cart.clearCart();
        cartRepository.save(cart);
    }
}

