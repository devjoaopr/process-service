package com.process_service.dto.Tasks;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record TaskDTO(
        UUID id,
        @NotNull
        @Size(max = 250)
        String title,
        String observation,
        UUID caseId,
        @NotNull
        UUID entityId,
        @NotNull
        @Size(max = 150)
        String entityNames,
        @NotNull
        UUID responsibleId,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt,
        OffsetDateTime doneAt
) {
}