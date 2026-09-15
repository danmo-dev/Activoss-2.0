package com.datacenter.asset.infrastructure.adapters.out.database.repositories.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.owner.OwnershipTypeEntity;

import java.util.UUID;

public interface OwnershipTypeJpaRepository extends JpaRepository<OwnershipTypeEntity, UUID> {
    boolean existsByCode(String code);
}
