package com.process_service.dto.Locator;

public record LocatorFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
