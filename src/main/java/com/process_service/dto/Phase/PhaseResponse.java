package com.process_service.dto.Phase;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;
@Builder
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
