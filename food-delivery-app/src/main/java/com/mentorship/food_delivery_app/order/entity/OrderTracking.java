package com.mentorship.food_delivery_app.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_tracking")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_tracking_id")
    private UUID id;

    /**
     * Status this tracking entry represents. Many tracking rows (across orders
     * and across time) can point at the same status, so this is @ManyToOne.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_tracking_status_id", nullable = false)
    private OrderStatus orderStatus;

    /**
     * The order this tracking entry belongs to. One order has many tracking
     * entries (one per status change), so this is @ManyToOne.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_tracking_order_id", nullable = false)
    private Orders order;

    @Column(name = "order_tracking_description")
    private String orderTrackingDescription;

    @Column(name = "order_tracking_created_at")
    @CreationTimestamp
    private Instant orderTrackingCreatedAt;
}
