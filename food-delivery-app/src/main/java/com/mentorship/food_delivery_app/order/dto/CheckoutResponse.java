package com.mentorship.food_delivery_app.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutResponse(
        UUID orderId,
        String restaurantName,
        BigDecimal orderFee,
        BigDecimal orderDiscountValue,
        BigDecimal orderTotal,
        String orderNote
) {
}
