package com.mentorship.food_delivery_app.customer.repository;

import com.mentorship.food_delivery_app.customer.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {
}
