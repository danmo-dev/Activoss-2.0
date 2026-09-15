package com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetRelationshipTypeEntity;

import java.util.UUID;

public interface AssetRelationshipTypeJpaRepository extends JpaRepository<AssetRelationshipTypeEntity, UUID> {
    boolean existsByCode(String code);
}