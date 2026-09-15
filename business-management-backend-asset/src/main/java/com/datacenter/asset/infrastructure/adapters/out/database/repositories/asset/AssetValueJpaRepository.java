package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetValueEntity;

import java.util.List;
import java.util.UUID;

public interface AssetValueJpaRepository extends JpaRepository<AssetValueEntity, UUID> {
    List<AssetValueEntity> findByAssetId(UUID assetId);
}