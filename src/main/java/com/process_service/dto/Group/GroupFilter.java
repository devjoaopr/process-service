package com.process_service.dto.Group;

public record GroupFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
