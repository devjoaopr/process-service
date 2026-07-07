package com.process_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cases {
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name = "updated_by_id")
    private UUID updatedById;
    @Column(name = "created_by_id")
    private UUID createdById;
    @Column(name = "observation", length = Integer.MAX_VALUE)
    private String observation;
    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    @NotNull
    @Column(name = "entity_id", nullable = false)
    private UUID entityId;
    @Size(max = 150)
    @NotNull
    @Column(name = "entity_names", nullable = false, length = 150)
    private String entityNames;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

}
