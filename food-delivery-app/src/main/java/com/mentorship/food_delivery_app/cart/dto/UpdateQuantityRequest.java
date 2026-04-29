package com.mentorship.food_delivery_app.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/** Body for {@code PUT /api/v1/carts/items/{itemId}}. */
public record UpdateQuantityRequest(
        UUID customerId,
        @JsonProperty("itemId") @NotNull Long cartItemId ,
        @Min(1) int quantity
) {
}
