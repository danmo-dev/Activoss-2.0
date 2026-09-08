package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.configuration.AssetType;
import com.datacenter.asset.domain.ports.in.ManageAssetTypeUseCase;
import com.datacenter.asset.domain.ports.out.AssetTypeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetTypeService implements ManageAssetTypeUseCase {

    private final AssetTypeRepositoryPort repositoryPort;

    public AssetTypeService(AssetTypeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetType create(AssetType assetType) {
        if (repositoryPort.existsByCode(assetType.getCode())) {
            throw new RuntimeException("Asset type already exists with code: " + assetType.getCode());
        }
        return repositoryPort.save(assetType);
    }

    @Override
    public List<AssetType> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public AssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
    }

    @Override
    public AssetType update(UUID id, String code, String name, String description) {
        AssetType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new RuntimeException("Asset type already exists with code: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        existing.setDescription(description);

        return repositoryPort.save(existing);
    }

    @Override
    public AssetType activate(UUID id) {
        AssetType assetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
        assetType.setActive(true);
        return repositoryPort.save(assetType);
    }

    @Override
    public AssetType deactivate(UUID id) {
        AssetType assetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
        assetType.setActive(false);
        return repositoryPort.save(assetType);
    }
}
