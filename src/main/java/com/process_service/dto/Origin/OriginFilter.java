package com.process_service.dto.Origin;

import java.util.List;

public record OriginFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
