package com.codewithahmed.ecommerce.cart;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponentsBuilder){
        CartDto createdCart = cartService.createCart();
        var uri = uriComponentsBuilder.path("/api/v1/products/{id}").buildAndExpand(createdCart.getId()).toUri();
        return ResponseEntity.created(uri).body(createdCart);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable Long cartId,
                                                     @RequestBody AddItemToCartRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addItemToCart(cartId,request));
    }

}
