package com.process_service.dto.Tasks;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record UpdateTaskRequest(
        UUID id,
        @Size(max = 250)
        String title,
        String observation,
        UUID caseId,
        UUID entityId,
        @Size(max = 150)
        String entityNames,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime updatedAt
) {
}
