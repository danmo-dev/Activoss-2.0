package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetEntity {

    @Id
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "asset_type_id", nullable = false)
    private UUID assetTypeId;

    @Column(name = "sub_asset_type_id")
    private UUID subAssetTypeId;

    @Column(name = "ownership_type_id", nullable = false)
    private UUID ownershipTypeId;

    @Column(name = "asset_status_id", nullable = false)
    private UUID assetStatusId;

    @Column(name = "location_id", nullable = false)
    private UUID locationId;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "qr_code", length = 255)
    private String qrCode;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}