package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.cart.Cart;
import com.codewithahmed.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AccountStatus Status;
    private String phone;
    private boolean is_active;
    private boolean email_verified;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
