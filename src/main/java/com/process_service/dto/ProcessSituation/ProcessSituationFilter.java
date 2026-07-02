package com.process_service.dto.ProcessSituation;

import lombok.Builder;

import java.util.List;

@Builder
public record ProcessSituationFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
