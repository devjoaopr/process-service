package com.process_service.shared;

import java.time.Instant;
import java.util.List;

public record ApiError(
        Instant timestamp,
        String message,
        String path,
        List<ApiFieldError> errors
) {
}
