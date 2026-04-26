package com.mentorship.food_delivery_app.customer.repository;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @EntityGraph(attributePaths = {"cart"})
    Optional<Customer> findCustomerWithCartByCustomerId(UUID customerId);


    @EntityGraph(attributePaths = {"cart", "cart.cartItems"})
    Optional<Customer> findCustomerWithFullCartInfoByCustomerId(UUID customerId);

    List<Customer> cart(Cart cart);
}
