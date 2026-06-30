package com.process_service.dto.ActionObject;

import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateActionObjectRequest(
        UUID id,
        @Size(max = 150)
        String name,
        @Size(max = 150)
        String slug,
        String description,
        Boolean active,
        Integer displayOrder,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt

) {
}
