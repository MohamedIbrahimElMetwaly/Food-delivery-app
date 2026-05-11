package com.mentorship.food_delivery_app.order.controller;

import com.mentorship.food_delivery_app.order.dto.CheckoutRequest;
import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;
import com.mentorship.food_delivery_app.order.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return new ResponseEntity<CheckoutResponse>(orderService.placeOrder(checkoutRequest), HttpStatus.CREATED);
    }
}
