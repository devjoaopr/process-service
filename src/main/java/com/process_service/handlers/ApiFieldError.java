package com.process_service.handlers;

public record ApiFieldError(
        String field,
        String message
) {
}
