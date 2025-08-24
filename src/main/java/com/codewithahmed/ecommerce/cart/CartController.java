package com.codewithahmed.ecommerce.cart;

import jakarta.validation.Valid;
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

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId){
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(@PathVariable Long cartId,
                                                  @PathVariable Long productId,
                                                  @Valid @RequestBody UpdateCartItemRequest request){
        CartItemDto cartItem = cartService.updateCartItem(cartId,productId,request);
        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(cartItem);
        }


    }
}
