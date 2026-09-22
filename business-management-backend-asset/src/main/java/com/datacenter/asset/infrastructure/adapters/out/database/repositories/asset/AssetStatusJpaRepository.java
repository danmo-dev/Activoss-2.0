package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetStatusEntity;

import java.util.Optional;
import java.util.UUID;

public interface AssetStatusJpaRepository extends JpaRepository<AssetStatusEntity, UUID> {
    Optional<AssetStatusEntity> findByCode(String code);

    boolean existsByCode(String code);
}
