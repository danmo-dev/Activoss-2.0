package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetStatusEntity;

import java.util.UUID;

public interface AssetStatusJpaRepository extends JpaRepository<AssetStatusEntity, UUID> {

}
