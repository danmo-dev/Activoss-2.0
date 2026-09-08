package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.configuration.SubAssetType;

import java.util.List;
import java.util.UUID;

public interface ManageSubAssetTypeUseCase {
    SubAssetType create(SubAssetType subAssetType);
    List<SubAssetType> findAll();
    List<SubAssetType> findByAssetTypeId(UUID assetTypeId);
    SubAssetType findById(UUID id);
    SubAssetType update(UUID id, String code, String name, String description);
    SubAssetType activate(UUID id);
    SubAssetType desactivate(UUID id);
}
