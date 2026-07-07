package com.process_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "done_at")
    private OffsetDateTime doneAt;
    @Column(name = "updated_by_id")
    private UUID updatedById;
    @Column(name = "created_by_id")
    private UUID createdById;
    @Column(name = "observation", length = Integer.MAX_VALUE)
    private String observation;
    @NotNull
    @Column(name = "responsible_id", nullable = false)
    private UUID responsibleId;
    @NotNull
    @Column(name = "entity_id", nullable = false)
    private UUID entityId;
    @Size(max = 150)
    @NotNull
    @Column(name = "entity_names", nullable = false, length = 150)
    private String entityNames;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "case_id", nullable = false)
    private Cases caseField;
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Size(max = 250)
    @NotNull
    @Column(name = "title", nullable = false, length = 250)
    private String title;
}
