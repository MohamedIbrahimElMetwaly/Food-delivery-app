package com.mentorship.food_delivery_app.cart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/** Body for {@code POST /api/v1/cart/addItem}. */
public record AddCartItemRequest(
        @NotNull UUID customerId,
        @NotNull @Valid CartItemPayload item
) {
}
