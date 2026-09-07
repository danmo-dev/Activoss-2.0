package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.asset.AssetValue;
import java.util.List;
import java.util.UUID;

public interface AssetValueRepositoryPort {
    List<AssetValue> saveAll(List<AssetValue> values);
    List<AssetValue> findByAssetId(UUID assetId);
}