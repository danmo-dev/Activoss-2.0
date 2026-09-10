package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetRelationshipEntity;

import java.util.List;
import java.util.UUID;

public interface AssetRelationshipJpaRepository extends JpaRepository<AssetRelationshipEntity, UUID> {
    List<AssetRelationshipEntity> findByParentAssetId(UUID parentAssetId);
}
