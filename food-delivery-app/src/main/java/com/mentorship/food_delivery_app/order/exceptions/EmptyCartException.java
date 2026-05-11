package com.mentorship.food_delivery_app.order.exceptions;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message) {super(message);}
}
