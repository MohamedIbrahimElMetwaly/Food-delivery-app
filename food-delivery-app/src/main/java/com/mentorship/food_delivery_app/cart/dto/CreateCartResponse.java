package com.mentorship.food_delivery_app.cart.dto;

import java.util.UUID;

public record CreateCartResponse(
        UUID cartId,
        UUID customerId
) {
}
