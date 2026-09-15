package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.models.asset.AssetCode;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.exception.AssetNotFoundException;
import com.datacenter.asset.domain.exception.DuplicateAssetCodeException;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepositoryPort repositoryPort;

    public Asset create(Asset domainAsset) {
        return repositoryPort.save(domainAsset);
    }

    public List<Asset> findAll() {
        return repositoryPort.findAll();
    }

    public Asset findById(UUID id) {
        return repositoryPort.findById(new AssetId(id))
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with id: " + id));
    }

    public Asset findByCode(String code) {
        return repositoryPort.findByCode(AssetCode.of(code))
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with code: " + code));
    }

    public Asset update(UUID id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
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
