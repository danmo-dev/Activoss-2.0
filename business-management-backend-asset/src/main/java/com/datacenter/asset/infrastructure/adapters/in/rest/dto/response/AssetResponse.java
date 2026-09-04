package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {
    private UUID id;
    private UUID companyId;
    private UUID assetTypeId;
    private UUID subAssetTypeId;
    private UUID ownershipTypeId;
    private UUID assetStatusId;
    private UUID locationId;
    private UUID ownerId;
    private String code;
    private String name;
    private String description;
    private LocalDate registrationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
