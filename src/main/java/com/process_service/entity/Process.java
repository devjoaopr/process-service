package com.process_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Process {
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_by_id")
    private UUID updatedById;
    @Column(name = "created_by_id")
    private UUID createdById;
    @Column(name = "observation", length = Integer.MAX_VALUE)
    private String observation;
    @Column(name = "request_text", length = Integer.MAX_VALUE)
    private String requestText;
    @Column(name = "capture_movements", nullable = false)
    private Boolean captureMovements;
    @Column(name = "justice_secret", nullable = false)
    private Boolean justiceSecret;
    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite;
    @Column(name = "responsible_user_id")
    private UUID responsibleUserId;
    @Column(name = "state_uf", length = 2)
    private String stateUf;
    @Column(name = "county_id")
    private UUID countyId;
    @Column(name = "court_unit_id")
    private UUID courtUnitId;
    @Column(name = "locator_option_id")
    private UUID locatorOptionId;
    @Column(name = "phase_option_id")
    private UUID phaseOptionId;
    @Column(name = "origin_option_id")
    private UUID originOptionId;
    @Column(name = "partner_option_id")
    private UUID partnerOptionId;
    @Column(name = "conference_option_id")
    private UUID conferenceOptionId;
    @Column(name = "prognosis_option_id")
    private UUID prognosisOptionId;
    @Column(name = "detail_option_id")
    private UUID detailOptionId;
    @Column(name = "subject_option_id")
    private UUID subjectOptionId;
    @Column(name = "action_object_option_id")
    private UUID actionObjectOptionId;
    @Column(name = "practice_area_option_id")
    private UUID practiceAreaOptionId;
    @Column(name = "group_option_id")
    private UUID groupOptionId;
    @Column(name = "process_type_option_id", nullable = false)
    private UUID processTypeOptionId;
    @Column(name = "situation_option_id", nullable = false)
    private UUID situationOptionId;
    @Column(name = "old_process_number", length = 80)
    private String oldProcessNumber;
    @Column(name = "cnj_number", length = 30)
    private String cnjNumber;
    @Column(name = "folder_number", length = 50)
    private String folderNumber;
    @Column(name = "internal_code")
    private Long internalCode;
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
}
