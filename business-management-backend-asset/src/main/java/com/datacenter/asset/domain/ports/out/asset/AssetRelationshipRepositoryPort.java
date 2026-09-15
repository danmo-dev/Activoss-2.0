package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import java.util.List;
import java.util.UUID;

public interface AssetRelationshipRepositoryPort {
    AssetRelationship save(AssetRelationship relationship);
    List<AssetRelationship> findByParentAssetId(UUID parentAssetId);
}