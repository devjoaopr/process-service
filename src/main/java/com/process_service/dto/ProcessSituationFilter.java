package com.process_service.dto;

import java.util.List;

public record ProcessSituationFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
