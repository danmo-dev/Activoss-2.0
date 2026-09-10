package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface AssetTypeJpaRepository
        extends JpaRepository<AssetTypeEntity, UUID> {

    Optional<AssetTypeEntity> findByCode(String code);

    boolean existsByCode(String code);
}