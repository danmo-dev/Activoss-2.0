package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface AssetTypeJpaRepository
        extends JpaRepository<AssetTypeEntity, UUID> {

    Optional<AssetTypeEntity> findByCode(String code);

    boolean existsByCode(String code);
}