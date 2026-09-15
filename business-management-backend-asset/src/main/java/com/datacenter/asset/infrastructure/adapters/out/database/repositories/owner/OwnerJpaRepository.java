package com.datacenter.asset.infrastructure.adapters.out.database.repositories.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.owner.OwnerEntity;

import java.util.List;
import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, UUID> {
    List<OwnerEntity> findByCompanyId(UUID companyId);
}
