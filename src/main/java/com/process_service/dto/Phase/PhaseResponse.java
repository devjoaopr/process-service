package com.process_service.dto.Phase;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PhaseResponse(
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
