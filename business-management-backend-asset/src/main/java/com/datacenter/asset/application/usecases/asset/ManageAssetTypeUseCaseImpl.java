package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetTypeUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetTypeUseCaseImpl implements ManageAssetTypeUseCase {

    private final AssetTypeRepositoryPort repositoryPort;

    @Override
    @Transactional
    public AssetType create(AssetType assetType) {
        if (repositoryPort.existsByCode(assetType.getCode())) {
            throw new BusinessException("El tipo de activo ya existe con el código: " + assetType.getCode());
        }
        return repositoryPort.save(assetType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetType> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de activo no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public AssetType update(UUID id, String code, String name, String description) {
        AssetType existing = findById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new BusinessException("El tipo de activo ya existe con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        existing.setDescription(description);

        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public AssetType activate(UUID id) {
        AssetType assetType = findById(id);
        assetType.setActive(true);
        return repositoryPort.save(assetType);
    }

    @Override
    @Transactional
    public AssetType deactivate(UUID id) {
        AssetType assetType = findById(id);
        assetType.setActive(false);
        return repositoryPort.save(assetType);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AssetType existing = findById(id);
        repositoryPort.deleteById(existing.getId());
    }
}