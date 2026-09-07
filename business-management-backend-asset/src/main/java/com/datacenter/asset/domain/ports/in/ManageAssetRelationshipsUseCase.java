package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.asset.AssetRelationship;
import java.util.List;
import java.util.UUID;

public interface ManageAssetRelationshipsUseCase {
    AssetRelationship createRelationship(AssetRelationship relationship);
    List<AssetRelationship> findChildrenByParentId(UUID parentAssetId);
}