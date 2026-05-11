package com.mentorship.food_delivery_app.customer.service.impl;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.repository.CustomerRepository;
import com.mentorship.food_delivery_app.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer getCustomerWithCart(UUID customerId) {
        return customerRepository.findCustomerWithCartById(customerId).orElseThrow();
    }

    @Override
    public Customer getCustomerWithFullCart(UUID customerId) {
        return customerRepository.findCustomerWithFullCartInfoById(customerId).orElseThrow();
    }
}
