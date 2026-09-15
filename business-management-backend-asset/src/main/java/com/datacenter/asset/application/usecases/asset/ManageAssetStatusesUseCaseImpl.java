package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetStatusesUseCase;
import com.datacenter.asset.application.service.asset.AssetStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetStatusesUseCaseImpl implements ManageAssetStatusesUseCase {

    private final AssetStatusService assetStatusService;

    @Override
    @Transactional
    public AssetStatus createAssetStatus(String code, String name) {
        return assetStatusService.create(code, name);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetStatus getById(UUID id) {
        return assetStatusService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetStatus> getAllAssetStatuses() {
        return assetStatusService.findAll();
    }

    @Override
    @Transactional
    public AssetStatus update(UUID id, String code, String name) {
        return assetStatusService.update(id, code, name);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        assetStatusService.delete(id);
    }
}