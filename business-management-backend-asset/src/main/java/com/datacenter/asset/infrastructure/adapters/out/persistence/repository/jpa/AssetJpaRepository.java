package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

// 1. Añadido JpaSpecificationExecutor para poder usar AssetSpecification
// 2. Eliminados los métodos con firmas falsas que causaban el error de Spring (No property 'findAssignedToPerson' found)
public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID>, JpaSpecificationExecutor<AssetEntity> {
    
    Optional<AssetEntity> findByCode(String code);
    
    boolean existsByCode(String code);
}