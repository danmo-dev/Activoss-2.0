package com.datacenter.asset.infrastructure.adapters.owner.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.owner.out.persistence.entity.OwnershipTypeEntity;

import java.util.UUID;

public interface OwnershipTypeJpaRepository extends JpaRepository<OwnershipTypeEntity, UUID> {

}
