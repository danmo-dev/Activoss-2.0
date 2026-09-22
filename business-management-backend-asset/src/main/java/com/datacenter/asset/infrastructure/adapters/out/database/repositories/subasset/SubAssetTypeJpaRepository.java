package com.datacenter.asset.infrastructure.adapters.out.database.repositories.subasset;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.subasset.SubAssetTypeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
public interface SubAssetTypeJpaRepository
        extends JpaRepository<SubAssetTypeEntity, UUID> {
 
    Optional<SubAssetTypeEntity> findByCode(String code);
 
    boolean existsByCode(String code);
 
    List<SubAssetTypeEntity> findByAssetTypeId(UUID assetTypeId);
}