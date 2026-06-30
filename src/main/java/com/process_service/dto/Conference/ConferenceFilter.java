package com.process_service.dto.Conference;

import java.util.List;

public record ConferenceFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
