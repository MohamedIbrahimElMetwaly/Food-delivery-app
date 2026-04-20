package com.mentorship.food_delivery_app.cart.exceptions;

import java.util.UUID;

/** Thrown when a cart referenced by id (or by customer) does not exist. */
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message) {
        super(message);
    }

    public static CartNotFoundException byId(UUID cartId) {
        return new CartNotFoundException("Cart not found: " + cartId);
    }

    public static CartNotFoundException byCustomer(UUID customerId) {
        return new CartNotFoundException("No open cart for customer: " + customerId);
    }
}
