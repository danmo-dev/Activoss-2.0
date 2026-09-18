package com.datacenter.asset.domain.models.asset;

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
    public void setParentAssetId(UUID parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    public void setChildAssetId(UUID childAssetId) {
        this.childAssetId = childAssetId;
    }

    public void setRelationshipTypeId(UUID relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }
}