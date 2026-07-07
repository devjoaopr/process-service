package com.process_service.dto.Cases;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CasesResponse(
        UUID id,
        String entityName,
        UUID entityId,
        String name,
        String observation,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {

}
