package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetLoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AssetLoanJpaRepository extends JpaRepository<AssetLoanEntity, UUID> {
    List<AssetLoanEntity> findByAssetId(UUID assetId);
    boolean existsByAssetIdAndStatus(UUID assetId, String status);
}
