package com.mentorship.food_delivery_app.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Nested payload inside {@link AddCartItemRequest}. Matches the Swagger
 * {@code CartItem} schema on the wire ({@code itemId}/{@code quantity}) but uses
 * the clearer internal name {@code menuItemId} — on the add-item flow the id
 * being sent is the catalog menu item, not the cart-line row id.
 */
public record CartItemPayload(
        @JsonProperty("itemId") @NotNull UUID menuItemId,
        @Min(1) int quantity,
        String note
) {
}
