package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.cart.Cart;
import com.codewithahmed.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
    private String phone;

    @OneToMany(mappedBy = "seller")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToOne(mappedBy = "user")
    private Cart cart;

    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }


}
