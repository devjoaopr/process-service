package com.process_service.dto.District;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DistrictResponse(
        OffsetDateTime deletedAt,
        OffsetDateTime updatedAt,
        OffsetDateTime createdAt,
        Boolean active,
        String judicialRank,
        String cnjId,
        String tjId,
        String internalId,
        String slug,
        String state,
        String name,
        UUID id

) {
}
