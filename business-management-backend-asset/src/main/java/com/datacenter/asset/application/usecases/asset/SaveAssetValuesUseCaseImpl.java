package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.ports.in.asset.SaveAssetValuesUseCase;
import com.datacenter.asset.application.service.asset.AssetValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaveAssetValuesUseCaseImpl implements SaveAssetValuesUseCase {

    private final AssetValueService assetValueService;

    @Override
    @Transactional
    public List<AssetValue> saveAssetValues(UUID assetId, List<AssetValue> values) {
        return assetValueService.save(assetId, values);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetValue> getAssetValues(UUID assetId) {
        return assetValueService.findByAsset(assetId);
    }
}
