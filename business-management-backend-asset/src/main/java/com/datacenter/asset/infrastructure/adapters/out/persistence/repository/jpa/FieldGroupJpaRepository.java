package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldGroupJpaRepository extends JpaRepository<FieldGroupEntity, UUID> {

    List<FieldGroupEntity> findBySubAssetTypeId(UUID subAssetTypeId);
}