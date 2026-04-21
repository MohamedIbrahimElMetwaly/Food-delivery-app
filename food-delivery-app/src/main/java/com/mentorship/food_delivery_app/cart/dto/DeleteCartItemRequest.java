package com.mentorship.food_delivery_app.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Body for {@code DELETE /api/v1/cart/item}.
 *
 * <p>The wire field {@code itemId} here refers to the cart-line row id
 * ({@code cart_item_id}, a DB-generated {@code BIGINT}) — the thing being
 * removed from the cart, not the menu item's UUID.
 */
public record DeleteCartItemRequest(
        @NotNull UUID cartId,
        @NotNull UUID customerId,
        @JsonProperty("itemId") @NotNull Long cartItemId
) {
}
