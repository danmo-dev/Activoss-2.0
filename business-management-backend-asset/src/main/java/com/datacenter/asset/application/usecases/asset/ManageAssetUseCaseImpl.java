package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.models.asset.AssetCode;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetUseCaseImpl implements ManageAssetUseCase {

    private final AssetRepositoryPort repositoryPort;

    @Override
    @Transactional
    public Asset createAsset(Asset asset) {
        return repositoryPort.save(asset);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Asset findById(UUID id) {
        return repositoryPort.findById(new AssetId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Asset findByCode(String code) {
        return repositoryPort.findByCode(AssetCode.of(code))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado con código: " + code));
    }

    @Override
    @Transactional
    public Asset updateAsset(UUID id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                             UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                             String code, String name, String description, LocalDate registrationDate) {
        Asset existingAsset = findById(id);

        if (!existingAsset.getCode().value().equals(code) && repositoryPort.existsByCode(AssetCode.of(code))) {
            throw new BusinessException("Ya existe un activo con el código: " + code);
        }

        existingAsset = existingAsset.update(companyId, assetTypeId, subAssetTypeId,
                ownershipTypeId, assetStatusId, locationId, ownerId,
                code, name, description, registrationDate);

        return repositoryPort.save(existingAsset);
    }
}