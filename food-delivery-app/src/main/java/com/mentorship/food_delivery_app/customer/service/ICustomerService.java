package com.mentorship.food_delivery_app.customer.service;

import com.mentorship.food_delivery_app.customer.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {
    Customer getCustomerWithCart(UUID customerId);
    Customer getCustomerWithFullCart(UUID customerId);

}
