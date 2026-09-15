package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface AssetHistoryJpaRepository extends JpaRepository<AssetHistoryEntity, UUID> {
    List<AssetHistoryEntity> findByAssetIdOrderByEventDateDesc(UUID assetId);
}