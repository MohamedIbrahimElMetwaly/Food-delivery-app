package com.mentorship.food_delivery_app.order.repository;

import com.mentorship.food_delivery_app.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Read-only access to the seeded {@code order_status} rows.
 *
 * <p>Service layer should look statuses up by {@link #findByName(String)}
 * (e.g. {@code findByName("PENDING")}) so the code never hard-codes the
 * IDENTITY ids produced by the seed insert.
 */
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

    Optional<OrderStatus> findByName(String name);
}
