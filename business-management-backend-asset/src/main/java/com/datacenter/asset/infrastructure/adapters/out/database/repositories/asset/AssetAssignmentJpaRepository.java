package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetAssignmentEntity;

import java.util.List;
import java.util.UUID;

public interface AssetAssignmentJpaRepository extends JpaRepository<AssetAssignmentEntity, UUID> {
    boolean existsByAssetIdAndIsActiveTrue(UUID assetId);

    List<AssetAssignmentEntity> findByPersonIdAndIsActiveTrue(UUID personId);
}