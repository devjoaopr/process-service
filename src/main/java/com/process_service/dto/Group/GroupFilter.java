package com.process_service.dto.Group;

import java.util.List;

public record GroupFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
