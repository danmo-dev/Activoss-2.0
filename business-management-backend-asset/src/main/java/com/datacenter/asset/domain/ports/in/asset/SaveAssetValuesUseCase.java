package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import java.util.List;
import java.util.UUID;

public interface SaveAssetValuesUseCase {
    List<AssetValue> saveAssetValues(UUID assetId, List<AssetValue> values);
    List<AssetValue> getAssetValues(UUID assetId);
    AssetValue update(UUID id, UUID fieldDefinitionId, String value);
    void delete(UUID id);
}