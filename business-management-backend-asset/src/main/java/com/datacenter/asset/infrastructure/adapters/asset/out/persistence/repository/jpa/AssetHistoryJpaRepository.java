package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface AssetHistoryJpaRepository extends JpaRepository<AssetHistoryEntity, UUID> {
    List<AssetHistoryEntity> findByAssetIdOrderByEventDateDesc(UUID assetId);
}