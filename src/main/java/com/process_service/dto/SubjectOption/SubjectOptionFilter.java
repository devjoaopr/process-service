package com.process_service.dto.SubjectOption;

public record SubjectOptionFilter(
        String description,
        String name,
        Boolean active,
        String slug
) {
}
