package com.mentorship.food_delivery_app.cart.entity;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.restaurant.entity.RestaurantBranch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Shopping cart for a customer. Backed by the {@code cart} table.
 *
 * <p>A cart is considered "open" while {@link #locked} is {@code false}. Once
 * checkout starts we flip the flag so no further mutations are accepted.
 */
@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_customer_id", nullable = false,updatable = false)
    private Customer customer;

    @Column(name = "is_locked", nullable = false)
    @Builder.Default
    private boolean locked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_current_rest_branch_id")
    private RestaurantBranch currentRestaurantBranch;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = jakarta.persistence.FetchType.LAZY
    )
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    // ---- Convenience helpers -------------------------------------------------

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
