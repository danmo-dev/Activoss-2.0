package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.asset.AssetValue;
import java.util.List;
import java.util.UUID;

public interface SaveAssetValuesUseCase {
    List<AssetValue> saveAssetValues(UUID assetId, List<AssetValue> values);
    List<AssetValue> getAssetValues(UUID assetId);
}