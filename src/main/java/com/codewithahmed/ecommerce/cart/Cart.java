package com.codewithahmed.ecommerce.cart;

import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.MERGE)
    List<CartItem> cartItems;
}
