package com.process_service.dto.Phase;

public record PhaseFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
