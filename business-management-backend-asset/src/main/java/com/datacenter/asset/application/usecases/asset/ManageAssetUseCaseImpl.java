package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetUseCase;
import com.datacenter.asset.application.service.asset.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetUseCaseImpl implements ManageAssetUseCase {

    private final AssetService assetService;

    @Override
    @Transactional
    public Asset createAsset(Asset asset) {
        return assetService.create(asset);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> findAll() {
        return assetService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Asset findById(UUID id) {
        return assetService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Asset findByCode(String code) {
        return assetService.findByCode(code);
    }

    @Override
    @Transactional
    public Asset updateAsset(UUID id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                             UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                             String code, String name, String description, LocalDate registrationDate) {
        return assetService.update(id, companyId, assetTypeId, subAssetTypeId,
                ownershipTypeId, assetStatusId, locationId, ownerId,
                code, name, description, registrationDate);
    }
}
