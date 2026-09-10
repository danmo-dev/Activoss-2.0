package com.datacenter.asset.infrastructure.adapters.fieldDefinition.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.fieldDefinition.out.persistence.entity.FieldDefinitionEntity;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionJpaRepository extends JpaRepository<FieldDefinitionEntity, UUID> {

    List<FieldDefinitionEntity> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinitionEntity> findByFieldGroupId(UUID fieldGroupId);
}