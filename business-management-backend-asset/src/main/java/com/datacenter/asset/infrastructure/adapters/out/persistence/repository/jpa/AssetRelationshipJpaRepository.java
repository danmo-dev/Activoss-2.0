package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetRelationshipJpaRepository extends JpaRepository<AssetRelationshipEntity, UUID> {
    List<AssetRelationshipEntity> findByParentAssetId(UUID parentAssetId);
}
