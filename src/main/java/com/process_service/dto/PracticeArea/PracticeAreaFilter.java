package com.process_service.dto.PracticeArea;

public record PracticeAreaFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
