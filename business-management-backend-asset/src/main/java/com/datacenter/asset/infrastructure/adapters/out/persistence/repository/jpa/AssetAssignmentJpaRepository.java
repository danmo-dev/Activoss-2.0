package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetAssignmentJpaRepository extends JpaRepository<AssetAssignmentEntity, UUID> {
    boolean existsByAssetIdAndIsActiveTrue(UUID assetId);

    List<AssetAssignmentEntity> findByPersonIdAndIsActiveTrue(UUID personId);
}