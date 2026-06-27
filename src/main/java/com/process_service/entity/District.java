package com.process_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class District {
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
    @Size(max = 50)
    @Column(name = "judicial_rank", length = 50)
    private String judicialRank;
    @Size(max = 50)
    @Column(name = "cnj_id", length = 50)
    private String cnjId;
    @Size(max = 50)
    @Column(name = "tj_id", length = 50)
    private String tjId;
    @Size(max = 50)
    @Column(name = "internal_id", length = 50)
    private String internalId;
    @Size(max = 150)
    @Column(name = "slug", length = 150)
    private String slug;
    @Size(max = 63)
    @Column(name = "state", length = 63)
    private String state;
    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

}
