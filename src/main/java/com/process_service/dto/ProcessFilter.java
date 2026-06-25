package com.process_service.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProcessFilter(
        Long internalCode,
        String folderNumber,
        String cnjNumber,
        String oldProcessNumber,
        List<UUID> situationOptionId,
        List<UUID> processTypeOptionId,
        List<UUID> groupOptionId,
        List<UUID> practiceAreaOptionId,
        List<UUID> actionObjectOptionId,
        List<UUID> subjectOptionId,
        List<UUID> detailOptionId,
        List<UUID> prognosisOptionId,
        List<UUID> conferenceOptionId,
        List<UUID> partnerOptionId,
        List<UUID> originOptionId,
        List<UUID> phaseOptionId,
        List<UUID> locatorOptionId,
        List<UUID> courtUnitId,
        List<UUID> countyId,
        String stateUf,
        UUID responsibleUserId,
        Boolean isFavorite,
        Boolean justiceSecret,
        Boolean captureMovements,
        String requestText,
        String observation,
        List<UUID> createdById,
        List<UUID> updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt

) {

}
