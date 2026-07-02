package com.process_service.dto.Phase;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;
@Builder

public record UpdatePhaseRequest(
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
