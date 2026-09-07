package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AssetValueJpaRepository extends JpaRepository<AssetValueEntity, UUID> {
    List<AssetValueEntity> findByAssetId(UUID assetId);
}