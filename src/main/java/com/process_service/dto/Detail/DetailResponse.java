package com.process_service.dto.Detail;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DetailResponse(
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
