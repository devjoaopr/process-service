package com.process_service.shared;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        List<ApiFieldError> errors = List.of(new ApiFieldError(
                "requestBody",
                ex.getCause().getMessage()
        ));

        ApiError error = new ApiError(
                Instant.now(),
                "Resource Not Found",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiFieldError> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiFieldError(
                        error.getField(),
                        error.getDefaultMessage()
                )).toList();
        ApiError error = new ApiError(
                Instant.now(),
                "Method Argument Not Valid, Bad Request",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        List<ApiFieldError> errors = List.of(new ApiFieldError(
                "requestBody",
                message
        ));
        ApiError error = new ApiError(
                Instant.now(),
                "Internal Server Error",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}