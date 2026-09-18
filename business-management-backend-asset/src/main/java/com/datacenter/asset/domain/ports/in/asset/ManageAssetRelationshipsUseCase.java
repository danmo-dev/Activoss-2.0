package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import java.util.List;
import java.util.UUID;

public interface ManageAssetRelationshipsUseCase {
    AssetRelationship createRelationship(AssetRelationship relationship);
    List<AssetRelationship> findChildrenByParentId(UUID parentAssetId);
    AssetRelationship update(UUID relationshipId, UUID newParentId, UUID newChildId, UUID newTypeId, String user);
    void delete(UUID relationshipId, String user);
}