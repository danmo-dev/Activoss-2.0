package com.datacenter.asset.domain.ports.asset.out;

import com.datacenter.asset.domain.asset.AssetRelationship;
import java.util.List;
import java.util.UUID;

public interface AssetRelationshipRepositoryPort {
    AssetRelationship save(AssetRelationship relationship);
    List<AssetRelationship> findByParentAssetId(UUID parentAssetId);
}