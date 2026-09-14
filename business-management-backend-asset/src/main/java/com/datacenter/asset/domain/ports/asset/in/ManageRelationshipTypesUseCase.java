package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;

import java.util.List;
import java.util.UUID;

public interface ManageRelationshipTypesUseCase {
    AssetRelationshipType createAssetRelationshipType(String code, String name);
    AssetRelationshipType getById(UUID id);
    List<AssetRelationshipType> getAllAssetRelationshipTypes();
    AssetRelationshipType update(UUID id, String code, String name);
    void delete(UUID id);
}