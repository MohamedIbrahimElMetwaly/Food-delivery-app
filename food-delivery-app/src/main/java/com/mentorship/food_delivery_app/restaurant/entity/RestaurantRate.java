package com.mentorship.food_delivery_app.restaurant.entity;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "restaurant_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "restaurant_rate_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_rate_restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_rate_customer_id", nullable = false)
    private Customer customer;

    @Column(name = "restaurant_rate_rating")
    private Integer rating;

    @Column(name = "restaurant_rate_comment", length = 500, nullable = false)
    private String comment;

    @CreationTimestamp
    @Column(name = "restaurant_rate_created_at", updatable = false)
    private LocalDateTime createdAt;
}