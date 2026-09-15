package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.asset.AssetHistory;
import java.util.List;
import java.util.UUID;

public interface AssetHistoryRepositoryPort {
    AssetHistory save(AssetHistory history);
    List<AssetHistory> findByAssetId(UUID assetId);
}