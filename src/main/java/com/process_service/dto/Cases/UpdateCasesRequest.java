package com.process_service.dto.Cases;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record UpdateCasesRequest(
        UUID id,
        @Size(max = 150)
        String entityName,
        UUID entityId,
        @Size(max = 150)
        String name,
        String observation,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt

) {
}
