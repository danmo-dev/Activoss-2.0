package com.datacenter.asset.infrastructure.adapters.subasset.out.persistence.repository.jpa;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.subasset.out.persistence.entity.SubAssetTypeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
public interface SubAssetTypeJpaRepository
        extends JpaRepository<SubAssetTypeEntity, UUID> {
 
    Optional<SubAssetTypeEntity> findByCode(String code);
 
    boolean existsByCode(String code);
 
    List<SubAssetTypeEntity> findByAssetTypeId(UUID assetTypeId);
}