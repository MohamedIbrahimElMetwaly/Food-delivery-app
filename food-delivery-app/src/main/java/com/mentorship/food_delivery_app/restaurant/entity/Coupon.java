package com.mentorship.food_delivery_app.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coupon_id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "coupon_amount", precision = 6, scale = 2)
    private BigDecimal amount;
    @Column(name = "coupon_available_from", nullable = false)
    private LocalDateTime availableFrom;

    @Column(name = "coupon_available_to", nullable = false)
    private LocalDateTime availableTo;

    @Column(name = "coupon_is_active", nullable = false)
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "coupon_created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "coupon_last_modified")
    private LocalDateTime lastModified;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_restaurant_id", nullable = false)
    private Restaurant restaurant;
}
