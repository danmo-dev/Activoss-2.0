package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetRelationshipEntity;

import java.util.List;
import java.util.UUID;

public interface AssetRelationshipJpaRepository extends JpaRepository<AssetRelationshipEntity, UUID> {
    List<AssetRelationshipEntity> findByParentAssetId(UUID parentAssetId);
}
