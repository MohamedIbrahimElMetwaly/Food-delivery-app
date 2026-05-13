package com.mentorship.food_delivery_app.order.service;

import com.mentorship.food_delivery_app.order.dto.CheckoutRequest;
import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;
import com.mentorship.food_delivery_app.order.dto.OrderDetailsResponse;

import java.util.UUID;

public interface IOrderService {

    CheckoutResponse placeOrder(CheckoutRequest checkoutRequest);
    void cancelOrder(UUID orderId);
    OrderDetailsResponse getOrderDetails(UUID orderId);
}
