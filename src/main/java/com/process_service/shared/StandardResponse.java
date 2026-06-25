package com.process_service.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class StandardResponse<T> {

    private T data;
    private boolean success;
    private String message;
    private Object errors;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public StandardResponse(T data, boolean success, String message,
                            Object errors, HttpStatus status) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}