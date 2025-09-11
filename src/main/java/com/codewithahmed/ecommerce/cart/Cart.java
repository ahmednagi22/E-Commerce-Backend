package com.codewithahmed.ecommerce.cart;

import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int itemsCount;
    private BigDecimal totalPrice;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true,fetch = FetchType.EAGER)
    List<CartItem> items = new ArrayList<>();

    public boolean isEmpty(){
        return items.isEmpty();
    }
    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : items) {
            total = total.add(cartItem.getTotalPrice());
        }
        return total;
    }
    // use information expert principle
    public CartItem getItem(Long productId) {
        return items
                .stream()
                .filter(Item ->
                        Item.getProduct().getId()
                                .equals(productId)).findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product,int quantity) {
        CartItem cartItem = getItem(product.getId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(this);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        CartItem cartItem = getItem(productId);
        items.remove(cartItem);
        cartItem.setCart(null);
    }

    public void clearCart() {
        items.clear();
    }
}
