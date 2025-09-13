package com.codewithahmed.ecommerce.order;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.cart.Cart;
import com.codewithahmed.ecommerce.payments.PaymentStatus;
import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private BigDecimal totalPrice;


    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<OrderItem> orderItems= new LinkedHashSet<>();

    public static Order fromCart(Cart cart, User user){
        var order = new Order();
        order.setUser(user);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());
        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(item.getProduct(),order,item.getQuantity());
            order.orderItems.add(orderItem);
        });
        return order;
    }

}
