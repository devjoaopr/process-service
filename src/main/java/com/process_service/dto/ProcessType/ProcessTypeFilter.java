package com.process_service.dto.ProcessType;

import java.util.List;

public record ProcessTypeFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
