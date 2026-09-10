package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetLoanEntity;

import java.util.List;
import java.util.UUID;

public interface AssetLoanJpaRepository extends JpaRepository<AssetLoanEntity, UUID> {
    List<AssetLoanEntity> findByAssetId(UUID assetId);

    boolean existsByAssetIdAndStatus(UUID assetId, String status);
}
