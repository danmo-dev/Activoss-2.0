package com.datacenter.asset.domain.asset;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AssetRelationship {
    private UUID id;
    private UUID parentAssetId;
    private UUID childAssetId;
    private UUID relationshipTypeId;
    private LocalDateTime registrationDate;
}