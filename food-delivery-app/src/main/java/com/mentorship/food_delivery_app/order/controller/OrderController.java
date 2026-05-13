package com.mentorship.food_delivery_app.order.controller;

import com.mentorship.food_delivery_app.order.dto.CheckoutRequest;
import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;
import com.mentorship.food_delivery_app.order.dto.OrderDetailsResponse;
import com.mentorship.food_delivery_app.order.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return new ResponseEntity<CheckoutResponse>(orderService.placeOrder(checkoutRequest), HttpStatus.CREATED);
    }
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
}
