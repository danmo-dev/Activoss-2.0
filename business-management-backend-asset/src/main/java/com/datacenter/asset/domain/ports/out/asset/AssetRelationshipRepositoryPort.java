package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRelationshipRepositoryPort {
    AssetRelationship save(AssetRelationship relationship);
    List<AssetRelationship> findByParentAssetId(UUID parentAssetId);
    Optional<AssetRelationship> findById(UUID id);
    void deleteById(UUID id);
}