package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetRelationshipTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssetRelationshipTypeJpaRepository extends JpaRepository<AssetRelationshipTypeEntity, UUID> {

}