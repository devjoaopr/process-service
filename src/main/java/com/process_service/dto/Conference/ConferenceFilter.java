package com.process_service.dto.Conference;

public record ConferenceFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
