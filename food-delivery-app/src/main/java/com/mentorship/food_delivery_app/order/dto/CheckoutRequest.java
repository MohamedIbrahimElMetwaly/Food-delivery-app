package com.mentorship.food_delivery_app.order.dto;

import com.mentorship.food_delivery_app.restaurant.entity.Coupon;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CheckoutRequest(
        @NotNull UUID cartId,
        List<Coupon> coupons,
        String orderNote
        ) {
}
