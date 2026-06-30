package com.process_service.dto.District;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record DistrictDTO(
        OffsetDateTime deletedAt,
        OffsetDateTime updatedAt,
        @NotNull
        Boolean active,
        @Size(max = 50)
        String judicialRank,
        @Size(max = 50)
        String cnjId,
        @Size(max = 50)
        String tjId,
        @Size(max = 50)
        String internalId,
        @Size(max = 150)
        String slug,
        @Size(max = 63)
        String state,
        @NotNull
        @Size(max = 150)
        String name

        ) {
}
