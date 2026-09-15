package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.domain.ports.out.asset.AssetTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetTypeService {

    private final AssetTypeRepositoryPort repositoryPort;

    public AssetType create(AssetType assetType) {
        if (repositoryPort.existsByCode(assetType.getCode())) {
            throw new RuntimeException("Asset type already exists with code: " + assetType.getCode());
        }
        return repositoryPort.save(assetType);
    }

    public List<AssetType> findAll() {
        return repositoryPort.findAll();
    }

    public AssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
    }

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

    public AssetType activate(UUID id) {
        AssetType assetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
        assetType.setActive(true);
        return repositoryPort.save(assetType);
    }

    public AssetType deactivate(UUID id) {
        AssetType assetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
        assetType.setActive(false);
        return repositoryPort.save(assetType);
    }

    public void delete(UUID id) {
        AssetType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found with id: " + id));
        System.out.println("Eliminando tipo de activo con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}