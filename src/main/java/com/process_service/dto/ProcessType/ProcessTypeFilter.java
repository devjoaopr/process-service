package com.process_service.dto.ProcessType;

public record ProcessTypeFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
