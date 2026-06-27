package com.process_service.dto.Prognosis;

public record PrognosisFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
