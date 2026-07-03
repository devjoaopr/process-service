package com.process_service.dto.ProcessType;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ProcessTypeDTO(
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
