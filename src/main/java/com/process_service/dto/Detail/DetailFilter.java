package com.process_service.dto.Detail;

import java.util.List;

public record DetailFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
