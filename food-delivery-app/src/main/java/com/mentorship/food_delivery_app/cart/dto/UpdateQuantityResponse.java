package com.mentorship.food_delivery_app.cart.dto;

public record UpdateQuantityResponse(
        Long cartItemId,
        int quantity
) {
}
