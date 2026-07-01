package com.process_service.dto.Conference;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
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
