package com.process_service.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProcessDTO(
        UUID id,
        Long internalCode,
        String folderNumber,
        @Size(max = 30) String cnjNumber,
        String oldProcessNumber,
        @NotNull
        UUID situationOptionId,
        @NotNull
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
        @NotNull
        Boolean isFavorite,
        @NotNull
        Boolean justiceSecret,
        @NotNull
        Boolean captureMovements,
        String requestText,
        String observation,
        UUID createdById,
        UUID updatedById,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {}