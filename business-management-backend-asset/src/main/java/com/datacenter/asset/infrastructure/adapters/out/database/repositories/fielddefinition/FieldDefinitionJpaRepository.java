package com.datacenter.asset.infrastructure.adapters.out.database.repositories.fielddefinition;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.fielddefinition.FieldDefinitionEntity;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionJpaRepository extends JpaRepository<FieldDefinitionEntity, UUID> {

    List<FieldDefinitionEntity> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinitionEntity> findByFieldGroupId(UUID fieldGroupId);
}