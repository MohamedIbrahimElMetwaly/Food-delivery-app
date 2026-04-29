package com.mentorship.food_delivery_app.common.exceptions;

public class ConflictException extends RuntimeException {
    private final String code;

    public ConflictException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
