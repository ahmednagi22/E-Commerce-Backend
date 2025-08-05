package com.codewithahmed.ecommerce.address;

import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
