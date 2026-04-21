package com.mentorship.food_delivery_app.cart.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/** Body for {@code POST /api/v1/cart/create}. */
public record CreateCartRequest(
        @NotNull UUID customerId
) {
}
