package com.mentorship.food_delivery_app.order.repository;

import com.mentorship.food_delivery_app.order.entity.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderTrackingRepository extends JpaRepository<OrderTracking, UUID> {

}
