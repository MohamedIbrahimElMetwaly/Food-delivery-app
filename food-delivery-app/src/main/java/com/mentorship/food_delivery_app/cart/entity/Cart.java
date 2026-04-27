package com.mentorship.food_delivery_app.cart.entity;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.entity.RestaurantBranch;
import jakarta.persistence.*;
import lombok.*;

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

    public void addItem(MenuItem menuItem, int quantity) throws Exception {
            if(this.currentRestaurantBranch != null) {
                UUID currentRestaurantBranchId = this.getCurrentRestaurantBranch().getId();
                UUID menuItemRestaurantBranchId = menuItem.getMenu().getRestaurantBranch().getId();

                if(!currentRestaurantBranchId.equals(menuItemRestaurantBranchId)) {
                    throw new Exception("this item belongs to a different restaurant");
                }
            }

            else {
                this.currentRestaurantBranch = menuItem.getMenu().getRestaurantBranch();
            }

            this.getItems() .stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst()
                .ifPresentOrElse(cartItem -> cartItem.setQuantity(cartItem.getQuantity()+quantity),
                        ()-> {
                            this.items.add(
                                    CartItem.builder()
                                            .cart(this)
                                            .quantity(quantity)
                                            .menuItem(menuItem).build());

                        }
                        );
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
