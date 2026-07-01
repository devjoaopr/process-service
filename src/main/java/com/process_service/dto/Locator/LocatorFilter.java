package com.process_service.dto.Locator;

import java.util.List;

public record LocatorFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
