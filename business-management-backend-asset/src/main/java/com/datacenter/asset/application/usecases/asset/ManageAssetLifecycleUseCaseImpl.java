package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetLifecycleUseCase;
import com.datacenter.asset.application.service.asset.AssetLifecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetLifecycleUseCaseImpl implements ManageAssetLifecycleUseCase {

    private final AssetLifecycleService assetLifecycleService;

    @Override
    public void deactivateAsset(UUID assetId, String reason, String executedBy) {
        assetLifecycleService.deactivate(assetId, reason, executedBy);
    }

    @Override
    public void reactivateAsset(UUID assetId, String reason, String executedBy) {
        assetLifecycleService.reactivate(assetId, reason, executedBy);
    }

    @Override
    public void decommissionAsset(UUID assetId, String reason, String executedBy) {
        assetLifecycleService.decommission(assetId, reason, executedBy);
    }

    @Override
    public List<AssetHistory> getAssetHistory(UUID assetId) {
        return assetLifecycleService.getHistory(assetId);
    }
}
