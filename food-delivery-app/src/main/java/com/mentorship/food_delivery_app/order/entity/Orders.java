package com.mentorship.food_delivery_app.order.entity;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.entity.CustomerAddress;
import com.mentorship.food_delivery_app.restaurant.entity.Coupon;
import com.mentorship.food_delivery_app.restaurant.entity.RestaurantBranch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_address_id", updatable = false, nullable = false)
    private CustomerAddress customerAddress;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_customer_id", nullable = false, updatable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_restaurant_branch_id")
    private RestaurantBranch restaurantBranch;

    // NOTE: @ManyToMany requires @JoinTable, not @JoinColumn — the existing
    // mapping below will not work. Phase-2 fix: define a join table or change
    // to @ManyToOne for a single coupon per order.
    @ManyToOne
    @JoinColumn(name = "order_coupon_id")
    private Coupon coupon;

    @Column(name = "order_discount_value", precision = 6, scale = 2)
    private BigDecimal orderDiscountValue;

    @Column(name = "order_subtotal")
    private BigDecimal orderSubtotal;

    @Column(name = "order_fee")
    private BigDecimal orderFee;

    @Column(name = "order_total")
    private BigDecimal orderTotal;

    @Column(name = "order_date")
    @CreationTimestamp
    private Instant orderDate;

    @Column(name = "order_note", updatable = false)
    private String orderNote;

    /**
     * Current status snapshot. Required (NOT NULL in schema). For history of
     * status changes, see {@link OrderTracking}.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    /**
     * Line items belonging to this order. The relationship is owned by
     * {@link OrderItem#order} (the FK column lives on {@code order_item}), so
     * here we use {@code mappedBy} and let the cascade persist new items when
     * the order is saved.
     */
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Convenience method that maintains both sides of the bidirectional link.
     * Always use this rather than {@code items.add(...)} directly.
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}