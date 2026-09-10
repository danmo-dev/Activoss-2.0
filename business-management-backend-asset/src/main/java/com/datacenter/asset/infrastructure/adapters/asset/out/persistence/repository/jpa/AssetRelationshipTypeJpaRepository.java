package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetRelationshipTypeEntity;

import java.util.UUID;

public interface AssetRelationshipTypeJpaRepository extends JpaRepository<AssetRelationshipTypeEntity, UUID> {

}