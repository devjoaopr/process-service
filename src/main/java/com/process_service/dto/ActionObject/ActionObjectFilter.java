package com.process_service.dto.ActionObject;

public record ActionObjectFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
