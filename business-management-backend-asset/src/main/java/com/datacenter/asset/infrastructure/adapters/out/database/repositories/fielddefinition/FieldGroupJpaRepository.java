package com.datacenter.asset.infrastructure.adapters.out.database.repositories.fielddefinition;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.fielddefinition.FieldGroupEntity;

import java.util.List;
import java.util.UUID;

public interface FieldGroupJpaRepository extends JpaRepository<FieldGroupEntity, UUID> {

    List<FieldGroupEntity> findBySubAssetTypeId(UUID subAssetTypeId);
}