package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionJpaRepository extends JpaRepository<FieldDefinitionEntity, UUID> {

    List<FieldDefinitionEntity> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinitionEntity> findByFieldGroupId(UUID fieldGroupId);
}