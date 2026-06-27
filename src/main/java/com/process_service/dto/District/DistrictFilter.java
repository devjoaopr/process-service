package com.process_service.dto.District;

import java.time.OffsetDateTime;
import java.util.List;

public record DistrictFilter(
        OffsetDateTime deletedAt,
        OffsetDateTime updatedAt,
        OffsetDateTime createdAt,
        Boolean active,
        String judicialRank,
        List<String> cnjId,
        List<String> tjId,
        List<String> internalId,
        List<String> slug,
        List<String> state,
        String name

) {
}
