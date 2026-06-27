package com.process_service.dto.Detail;

public record DetailFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
