package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.configuration.AssetStatus;

import java.util.List;
import java.util.UUID;

public interface ManageAssetStatusesUseCase {
    AssetStatus createAssetStatus(String code, String name);
    AssetStatus getById(UUID id);
    List<AssetStatus> getAllAssetStatuses();
    AssetStatus update(UUID id, String code, String name);
    void delete(UUID id);
}