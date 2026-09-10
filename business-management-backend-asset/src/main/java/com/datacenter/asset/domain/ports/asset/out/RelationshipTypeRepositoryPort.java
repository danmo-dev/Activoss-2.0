package com.datacenter.asset.domain.ports.asset.out;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;

import java.util.List;

public interface RelationshipTypeRepositoryPort {

    AssetRelationshipType save(
            AssetRelationshipType relationshipType
    );

    List<AssetRelationshipType> findAll();
}