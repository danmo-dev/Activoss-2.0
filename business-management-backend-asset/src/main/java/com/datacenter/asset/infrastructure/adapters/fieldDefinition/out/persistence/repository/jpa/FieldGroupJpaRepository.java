package com.datacenter.asset.infrastructure.adapters.fieldDefinition.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.fieldDefinition.out.persistence.entity.FieldGroupEntity;

import java.util.List;
import java.util.UUID;

public interface FieldGroupJpaRepository extends JpaRepository<FieldGroupEntity, UUID> {

    List<FieldGroupEntity> findBySubAssetTypeId(UUID subAssetTypeId);
}