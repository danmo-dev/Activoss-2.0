package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetValueRepositoryPort {
    List<AssetValue> saveAll(List<AssetValue> values);
    AssetValue save(AssetValue value);
    List<AssetValue> findByAssetId(UUID assetId);
    Optional<AssetValue> findById(UUID id);
    void deleteById(UUID id);
}