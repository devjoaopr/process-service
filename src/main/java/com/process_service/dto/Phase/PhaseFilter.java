package com.process_service.dto.Phase;

import java.util.List;

public record PhaseFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
