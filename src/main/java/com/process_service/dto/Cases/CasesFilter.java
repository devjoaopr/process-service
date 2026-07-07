package com.process_service.dto.Cases;

import java.util.List;

public record CasesFilter(
        List<String> observation,
        List<String> name,
        List<String> entityName
) {
}
