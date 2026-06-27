package com.process_service.dto.SubjectOption;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SubjectOptionResponse(
        UUID id,
        String name,
        String slug,
        String description,
        Boolean active,
        Integer displayOrder,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {

}
