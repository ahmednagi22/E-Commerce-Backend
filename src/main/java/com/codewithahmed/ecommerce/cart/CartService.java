package com.codewithahmed.ecommerce.cart;

import com.codewithahmed.ecommerce.common.exception.CartNotFoundException;
import com.codewithahmed.ecommerce.common.exception.ProductNotFoundException;
import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        // check cart id first
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(
                () -> new CartNotFoundException("Cart with id " + cartId + " not found.")
        );
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + request.getProductId() + " not found.")
        );

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(
                        Item -> Item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
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
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(Item ->
                        Item.getProduct().getId()
                                .equals(productId)).findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(request.getQuantity());
            cartRepository.save(cart);
            return cartMapper.toCartItemDto(cartItem);
        } else {
            return null;
        }
    }
}

