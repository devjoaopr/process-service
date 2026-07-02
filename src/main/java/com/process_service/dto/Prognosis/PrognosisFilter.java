package com.process_service.dto.Prognosis;

import lombok.Builder;

import java.util.List;

@Builder
public record PrognosisFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
