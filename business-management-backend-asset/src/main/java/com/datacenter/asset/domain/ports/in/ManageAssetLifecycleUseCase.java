package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.asset.AssetHistory;
import java.util.List;
import java.util.UUID;

public interface ManageAssetLifecycleUseCase {
    void deactivateAsset(UUID assetId, String reason, String executedBy);
    void reactivateAsset(UUID assetId, String reason, String executedBy);
    void decommissionAsset(UUID assetId, String reason, String executedBy);
    List<AssetHistory> getAssetHistory(UUID assetId);
}