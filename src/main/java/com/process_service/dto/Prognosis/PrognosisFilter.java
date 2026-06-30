package com.process_service.dto.Prognosis;

import java.util.List;

public record PrognosisFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
