package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.exception.AssetNotFoundException;
import com.datacenter.asset.domain.exception.DuplicateAssetCodeException;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetUseCase;
import com.datacenter.asset.domain.ports.asset.out.AssetRepositoryPort;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AssetService implements ManageAssetUseCase {

    private final AssetRepositoryPort repositoryPort;

    public AssetService(AssetRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Asset createAsset(Asset domainAsset) {
        return repositoryPort.save(domainAsset);
    }

    @Override
    public List<Asset> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Asset findById(UUID id) {
        return repositoryPort.findById(new AssetId(id))
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with id: " + id));
    }

    @Override
    public Asset findByCode(String code) {
        return repositoryPort.findByCode(AssetCode.of(code))
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with code: " + code));
    }

    @Override
    public Asset updateAsset(UUID id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                             UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                             String code, String name, String description, LocalDate registrationDate) {

        Asset existingAsset = repositoryPort.findById(new AssetId(id))
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with id: " + id));

        if (!existingAsset.getCode().value().equals(code) && repositoryPort.existsByCode(AssetCode.of(code))) {
            throw new DuplicateAssetCodeException("An asset already exists with code: " + code);
        }

        existingAsset = existingAsset.update(companyId, assetTypeId, subAssetTypeId,
                ownershipTypeId, assetStatusId, locationId, ownerId,
                code, name, description, registrationDate);

        return repositoryPort.save(existingAsset);
    }
}
