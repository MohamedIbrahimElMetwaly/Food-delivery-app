package com.mentorship.food_delivery_app.order.entity;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A single line on an order. Each {@link Orders} has many {@code OrderItem}s.
 *
 * <p>Important design choice: this entity stores a price snapshot
 * ({@code unitPrice}) at the moment of checkout. We deliberately do NOT compute
 * totals from {@code menuItem.getPrice()} at read time, because the menu price
 * can change after the order is placed. Whatever the customer agreed to is what
 * shows on the receipt.
 */
@Entity
@Table(name = "order_item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_item_id", updatable = false, nullable = false)
    private UUID id;

    /** The order this line belongs to. Many lines per order. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_order_id", nullable = false, updatable = false)
    private Orders order;

    /**
     * Catalog item being ordered. {@code @ManyToOne} because many order_item
     * rows (across all orders) can reference the same MenuItem.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_menu_item_id", nullable = false, updatable = false)
    private MenuItem menuItem;

    /** Price per unit AT TIME OF CHECKOUT — not derived from menuItem.getPrice() at read time. */
    @Column(name = "order_item_unit_price", nullable = false, precision = 9, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "order_item_quantity", nullable = false)
    private int quantity;

    /** {@code unitPrice * quantity}, materialised so we don't recompute on every read. */
    @Column(name = "order_item_subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "order_item_note", length = 255)
    private String note;
}
