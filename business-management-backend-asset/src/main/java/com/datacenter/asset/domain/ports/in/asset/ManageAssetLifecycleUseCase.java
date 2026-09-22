package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.asset.AssetHistory;
import java.util.List;
import java.util.UUID;

public interface ManageAssetLifecycleUseCase {
    void deactivateAsset(UUID assetId, String reason, String executedBy);
    void reactivateAsset(UUID assetId, String reason, String executedBy);
    void decommissionAsset(UUID assetId, String reason, String executedBy);
    List<AssetHistory> getAssetHistory(UUID assetId);
}