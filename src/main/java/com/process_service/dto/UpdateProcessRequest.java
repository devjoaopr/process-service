package com.process_service.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UpdateProcessRequest(
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
        String subjectOptionId,
        UUID detailOptionId,
        UUID prognosisOptionId,
        UUID conferenceOptionId,
        UUID partnerOptionId,
        UUID originOptionId,
        UUID phaseOptionId,
        UUID locatorOptionId,
        UUID courtUnitId,
        UUID countyId,
        @Size(max = 2)
        String stateUf,
        UUID responsibleUserId,
        Boolean isFavorite,
        Boolean justiceSecret,
        Boolean captureMovements,
        @Size(max = 100)
        String requestText,
        @Size(max = 100)
        String observation,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {

}
