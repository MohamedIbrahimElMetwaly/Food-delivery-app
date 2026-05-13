package com.mentorship.food_delivery_app.order.repository;

import com.mentorship.food_delivery_app.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.items WHERE o.id = :orderId")
    Optional<Orders> findByIdWithItems(@Param("orderId") UUID orderId);
}
