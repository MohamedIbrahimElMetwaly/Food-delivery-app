package com.mentorship.food_delivery_app.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Shape returned by {@code GlobalExceptionHandler} for every error.
 *
 * @param code        machine-readable error code (e.g. {@code CART_NOT_FOUND})
 * @param message     human-readable summary
 * @param timestamp   when the error was produced
 * @param path        request path that triggered the error
 * @param fieldErrors per-field validation errors, empty for non-validation errors
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        String code,
        String message,
        Instant timestamp,
        String path,
        List<FieldError> fieldErrors
) {

    public record FieldError(String field, String message) {
    }

    public static ErrorResponse of(String code, String message, String path) {
        return new ErrorResponse(code, message, Instant.now(), path, List.of());
    }

    public static ErrorResponse of(String code, String message, String path,
                                   List<FieldError> fieldErrors) {
        return new ErrorResponse(code, message, Instant.now(), path, fieldErrors);
    }
}
