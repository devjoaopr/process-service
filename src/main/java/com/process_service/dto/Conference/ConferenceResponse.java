package com.process_service.dto.Conference;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ConferenceResponse(
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
