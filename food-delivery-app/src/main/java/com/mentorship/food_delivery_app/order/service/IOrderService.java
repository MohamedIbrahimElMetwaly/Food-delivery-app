package com.mentorship.food_delivery_app.order.service;

import com.mentorship.food_delivery_app.order.dto.CheckoutRequest;
import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;

public interface IOrderService {

    CheckoutResponse placeOrder(CheckoutRequest checkoutRequest);


}
