package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetAssignmentEntity;

import java.util.List;
import java.util.UUID;

public interface AssetAssignmentJpaRepository extends JpaRepository<AssetAssignmentEntity, UUID> {
    boolean existsByAssetIdAndIsActiveTrue(UUID assetId);

    List<AssetAssignmentEntity> findByPersonIdAndIsActiveTrue(UUID personId);
}