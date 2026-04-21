package com.mentorship.food_delivery_app.cart.exceptions;

import java.util.UUID;

/**
 * Thrown when the {@code customerId} in a request body does not own the
 * {@code cartId} in that same request. Maps to HTTP 409 — the request is
 * syntactically valid, but its two halves disagree about whose cart it is.
 */
public class CartOwnershipMismatchException extends RuntimeException {

    public CartOwnershipMismatchException(String message) {
        super(message);
    }

    public static CartOwnershipMismatchException of(UUID cartId, UUID customerId) {
        return new CartOwnershipMismatchException(
                "Cart " + cartId + " does not belong to customer " + customerId);
    }
}
