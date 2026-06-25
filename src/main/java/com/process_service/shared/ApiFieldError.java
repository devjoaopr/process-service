package com.process_service.shared;

public record ApiFieldError(
        String field,
        String message
) {
}
