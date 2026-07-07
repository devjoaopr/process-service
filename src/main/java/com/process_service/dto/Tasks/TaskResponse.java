package com.process_service.dto.Tasks;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record TaskResponse(
        UUID id,
        String title,
        String observation,
        UUID caseId,
        UUID entityId,
        String entityNames,
        UUID responsibleId,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt,
        OffsetDateTime doneAt

) {

}
