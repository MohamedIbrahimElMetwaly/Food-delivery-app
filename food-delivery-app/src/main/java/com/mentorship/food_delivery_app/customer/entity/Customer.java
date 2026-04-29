package com.mentorship.food_delivery_app.customer.entity;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", updatable = false, nullable = false)
    private UUID customerId;

    @Column(name = "customer_user_id", nullable = false, unique = true)
    private UUID customerUserId;

    @OneToOne
    @JoinColumn(name = "customer_default_address_id", referencedColumnName = "customer_address_id")
    private CustomerAddress defaultAddress;

    @Column(name = "customer_preferred_payment_id")
    private Integer customerPreferredPaymentId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerAddress> addresses;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

}