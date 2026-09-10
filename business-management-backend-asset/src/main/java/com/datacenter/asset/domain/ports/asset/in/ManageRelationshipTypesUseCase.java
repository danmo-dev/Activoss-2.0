package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;

import java.util.List;

public interface ManageRelationshipTypesUseCase {

    AssetRelationshipType createAssetRelationshipType(
            String code,
            String name
    );

    List<AssetRelationshipType> getAllAssetRelationshipTypes();
}