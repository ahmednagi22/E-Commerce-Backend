package order;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
