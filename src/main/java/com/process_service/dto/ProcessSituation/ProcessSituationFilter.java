package com.process_service.dto.ProcessSituation;

import java.util.List;

public record ProcessSituationFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
