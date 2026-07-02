package com.process_service.dto.Locator;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record LocatorDTO(
        UUID id,
        @Size(max = 150)
        String name,
        @Size(max = 150)
        String slug,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {
}
