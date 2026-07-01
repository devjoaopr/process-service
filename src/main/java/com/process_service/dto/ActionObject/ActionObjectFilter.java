package com.process_service.dto.ActionObject;

import java.util.List;

public record ActionObjectFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
