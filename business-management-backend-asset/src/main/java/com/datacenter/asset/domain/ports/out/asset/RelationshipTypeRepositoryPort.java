package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelationshipTypeRepositoryPort {
    AssetRelationshipType save(AssetRelationshipType relationshipType);
    Optional<AssetRelationshipType> findById(UUID id);
    List<AssetRelationshipType> findAll();
    boolean existsByCode(String code);
    void deleteById(UUID id);
}