package com.process_service.dto.Origin;

public record OriginFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
