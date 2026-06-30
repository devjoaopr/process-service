package com.process_service.dto.ProcessSituation;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProcessSituationResponse(
        UUID id,
        String name,
        String slug,
        String description,
        Boolean active,
        Integer displayOrder,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {

}
