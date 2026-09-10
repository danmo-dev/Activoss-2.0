package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asset_assignments")
@Data
public class AssetAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "asset_id", nullable = false)
    private UUID assetId;

    @Column(name = "person_id", nullable = false)
    private UUID personId;

    @Column(name = "relationship_type_id")
    private UUID relationshipTypeId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "state", nullable = false)
    private String state = "PENDING"; // Valores: PENDING, ACCEPTED, REJECTED

    @Column(name = "acceptance_date")
    private LocalDateTime acceptanceDate;

    @Column(name = "pdf_path")
    private String pdfPath;

    @Column(name = "rejection_reason")
    private String rejectionReason;
}
