package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AssetHistoryJpaRepository extends JpaRepository<AssetHistoryEntity, UUID> {
    List<AssetHistoryEntity> findByAssetIdOrderByEventDateDesc(UUID assetId);
}