package com.mentorship.food_delivery_app.cart.exceptions;

import java.util.UUID;

/** Thrown when a cart item id does not belong to the given cart. */
public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(String message) {
        super(message);
    }

    public static CartItemNotFoundException of(UUID cartId, Long cartItemId) {
        return new CartItemNotFoundException(
                "Cart item " + cartItemId + " not found in cart " + cartId);
    }
}
