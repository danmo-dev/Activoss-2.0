package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetValueEntity;

import java.util.List;
import java.util.UUID;

public interface AssetValueJpaRepository extends JpaRepository<AssetValueEntity, UUID> {
    List<AssetValueEntity> findByAssetId(UUID assetId);
}