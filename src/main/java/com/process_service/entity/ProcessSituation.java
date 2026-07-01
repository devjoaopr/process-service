package com.process_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class ProcessSituation {
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "display_order")
    private Integer displayOrder;
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;
    @Size(max = 150)
    @NotNull
    @Column(name = "slug", nullable = false, length = 150)
    private String slug;
    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
}
