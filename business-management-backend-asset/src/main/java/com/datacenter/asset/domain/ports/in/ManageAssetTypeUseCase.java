package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.configuration.AssetType;

import java.util.List;
import java.util.UUID;

public interface ManageAssetTypeUseCase {
    AssetType create(AssetType assetType);
    List<AssetType> findAll();
    AssetType findById(UUID id);
    AssetType update(UUID id, String code, String name, String description);
    AssetType activate(UUID id);
    AssetType deactivate(UUID id);
}
