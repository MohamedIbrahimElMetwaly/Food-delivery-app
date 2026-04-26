package com.mentorship.food_delivery_app.cart.entity;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * A single line in a {@link Cart}. Backed by the {@code cart_item} table.
 *
 * <p>The primary key is a DB-generated {@code BIGINT} (IDENTITY). {@code menuItemId}
 * is the UUID of the catalog item being ordered — it is intentionally kept as a
 * plain UUID rather than a {@code @ManyToOne} relationship until the Menu module
 * lands, to avoid cross-module entity coupling in Phase 1.
 */
@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_item_cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_cart_id")
    private MenuItem menuItem;

    @Column(name = "cart_item_quantity", nullable = false)
    private int quantity;

    @Column(name = "cart_item_note", length = 255)
    private String note;
}
