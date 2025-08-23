package com.codewithahmed.ecommerce.cart;

import com.codewithahmed.ecommerce.category.Category;
import com.codewithahmed.ecommerce.common.exception.CartNotFoundException;
import com.codewithahmed.ecommerce.common.exception.CategoryNotFoundException;
import com.codewithahmed.ecommerce.common.exception.ProductNotFoundException;
import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.product.ProductDto;
import com.codewithahmed.ecommerce.product.ProductRepository;
import com.codewithahmed.ecommerce.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartNotFoundException("Cart with id " + cartId + " not found.")
        );
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + request.getProductId() + " not found.")
        );

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(
                Item -> Item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }
        else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem);
    }
}
