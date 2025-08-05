package com.codewithahmed.ecommerce.review;

import com.codewithahmed.ecommerce.product.Product;
import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
