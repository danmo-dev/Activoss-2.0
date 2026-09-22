package com.datacenter.asset.infrastructure.adapters.out.database.repositories.location;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.location.LocationEntity;

import java.util.UUID;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, UUID> {
    boolean existsByCode(String code);
}