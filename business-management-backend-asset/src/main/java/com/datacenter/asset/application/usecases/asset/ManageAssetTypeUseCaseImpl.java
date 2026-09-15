package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetTypeUseCase;
import com.datacenter.asset.application.service.asset.AssetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetTypeUseCaseImpl implements ManageAssetTypeUseCase {

    private final AssetTypeService assetTypeService;

    @Override
    @Transactional
    public AssetType create(AssetType assetType) {
        return assetTypeService.create(assetType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetType> findAll() {
        return assetTypeService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AssetType findById(UUID id) {
        return assetTypeService.findById(id);
    }

    @Override
    @Transactional
    public AssetType update(UUID id, String code, String name, String description) {
        return assetTypeService.update(id, code, name, description);
    }

    @Override
    @Transactional
    public AssetType activate(UUID id) {
        return assetTypeService.activate(id);
    }

    @Override
    @Transactional
    public AssetType deactivate(UUID id) {
        return assetTypeService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        assetTypeService.delete(id);
    }
}
