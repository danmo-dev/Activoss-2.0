package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssetTypeJpaRepository
        extends JpaRepository<AssetTypeEntity, UUID> {

    Optional<AssetTypeEntity> findByCode(String code);

    boolean existsByCode(String code);
}