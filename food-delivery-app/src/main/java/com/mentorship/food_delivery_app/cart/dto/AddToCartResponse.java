package com.mentorship.food_delivery_app.cart.dto;

import java.util.UUID;

public record AddToCartResponse(
        UUID cartId,
        Long cartItemId,
        UUID menuItemId,
        int quantity,
        String note
) {
}
