package com.mentorship.food_delivery_app.cart.exceptions;

import java.util.UUID;

/**
 * Thrown on any mutating operation against a cart whose {@code is_locked} flag
 * is {@code true} (checkout has started). Maps to HTTP 409.
 */
public class CartLockedException extends RuntimeException {

    public CartLockedException(String message) {
        super(message);
    }

    public static CartLockedException of(UUID cartId) {
        return new CartLockedException("Cart " + cartId + " is locked and cannot be modified");
    }
}
