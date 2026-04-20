package com.mentorship.food_delivery_app.cart.dto;

import jakarta.validation.constraints.Min;

/** Body for {@code PUT /api/v1/carts/items/{itemId}}. */
public record UpdateQuantityRequest(
        @Min(1) int quantity
) {
}
