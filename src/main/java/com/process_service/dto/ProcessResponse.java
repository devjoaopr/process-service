package com.process_service.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProcessResponse(
        UUID id,
        Long internalCode,
        String folderNumber,
        String cnjNumber,
        String oldProcessNumber,
        UUID situationOptionId,
        UUID processTypeOptionId,
        UUID groupOptionId,
        UUID practiceAreaOptionId,
        UUID actionObjectOptionId,
        UUID subjectOptionId,
        UUID detailOptionId,
        UUID prognosisOptionId,
        UUID conferenceOptionId,
        UUID partnerOptionId,
        UUID originOptionId,
        UUID phaseOptionId,
        UUID locatorOptionId,
        UUID courtUnitId,
        UUID countyId,
        String stateUf,
        UUID responsibleUserId,
        Boolean isFavorite,
        Boolean justiceSecret,
        Boolean captureMovements,
        String requestText,
        String observation,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {

}
