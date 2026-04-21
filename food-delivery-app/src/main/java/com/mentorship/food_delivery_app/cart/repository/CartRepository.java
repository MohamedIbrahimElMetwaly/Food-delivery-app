package com.mentorship.food_delivery_app.cart.repository;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    /**
     * Find the currently open (unlocked) cart for a customer. A customer may have
     * locked carts from completed checkouts sitting around, but at most one open
     * cart at a time — enforce that invariant in the service layer in Phase 2.
     */
    Optional<Cart> findByCustomerIdAndLockedFalse(UUID customerId);

    Optional<Cart> findByIdAndCustomerId(UUID id, UUID customerId);
}
