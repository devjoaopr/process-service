package com.process_service.dto.ProcessSituation;

public record ProcessSituationFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
