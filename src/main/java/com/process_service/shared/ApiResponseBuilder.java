package com.process_service.shared;

import org.springframework.http.HttpStatus;

public class ApiResponseBuilder {
    public static <T> StandardResponse<T> success(T data, String message) {
        return new StandardResponse<>(data, true, message, null, HttpStatus.OK);
    }

    public static <T> StandardResponse<T> error(String message, Object errors, HttpStatus status) {
        return new StandardResponse<>(null, false, message, errors, status);
    }
}
