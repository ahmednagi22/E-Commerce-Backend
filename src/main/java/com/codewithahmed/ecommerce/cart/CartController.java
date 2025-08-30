package com.codewithahmed.ecommerce.cart;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
//@Tag(name = "Carts")
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponentsBuilder) {
        CartDto createdCart = cartService.createCart();
        var uri = uriComponentsBuilder.path("/api/v1/products/{id}").buildAndExpand(createdCart.getId()).toUri();
        return ResponseEntity.created(uri).body(createdCart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PostMapping("/{cartId}/items")
//    @Operation(summary = "add item to cart using product id")
    public ResponseEntity<CartItemDto> addItemToCart(/*@Parameter name="cart id")*/ @PathVariable Long cartId,
                                                     @RequestBody AddItemToCartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addItemToCart(cartId, request));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(@PathVariable Long cartId,
                                                  @PathVariable Long productId,
                                                  @Valid @RequestBody UpdateCartItemRequest request) {
        CartItemDto cartItem = cartService.updateCartItem(cartId, productId, request);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(cartItem);
        }


    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartId,
                                           @PathVariable Long productId) {
        cartService.removeItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }


}
