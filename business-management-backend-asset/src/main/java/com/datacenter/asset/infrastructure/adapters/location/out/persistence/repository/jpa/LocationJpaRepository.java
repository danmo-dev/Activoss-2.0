package com.datacenter.asset.infrastructure.adapters.location.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.location.out.persistence.entity.LocationEntity;

import java.util.UUID;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, UUID> {
    boolean existsByCode(String code);
}