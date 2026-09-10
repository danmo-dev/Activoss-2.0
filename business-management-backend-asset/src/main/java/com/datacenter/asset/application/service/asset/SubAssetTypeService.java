package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.subasset.in.ManageSubAssetTypeUseCase;
import com.datacenter.asset.domain.ports.subasset.out.SubAssetTypeRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubAssetTypeService implements ManageSubAssetTypeUseCase {

    private final SubAssetTypeRepositoryPort repositoryPort;

    public SubAssetTypeService(SubAssetTypeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public SubAssetType create(SubAssetType subAssetType) {
        if (repositoryPort.existsByCode(subAssetType.getCode())) {
            throw new RuntimeException("Sub asset type already exists with code: " + subAssetType.getCode());
        }
        return repositoryPort.save(subAssetType);
    }

    @Override
    public List<SubAssetType> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public List<SubAssetType> findByAssetTypeId(UUID assetTypeId) {
        return repositoryPort.findByAssetTypeId(assetTypeId);
    }

    @Override
    public SubAssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
    }

    @Override
    public SubAssetType update(UUID id, String code, String name, String description) {
        SubAssetType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new RuntimeException("Sub asset type already exists with code: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        existing.setDescription(description);

        return repositoryPort.save(existing);
    }

    @Override
    public SubAssetType activate(UUID id) {
        SubAssetType subAssetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
        subAssetType.setActive(true);
        return repositoryPort.save(subAssetType);
    }

    @Override
    public SubAssetType desactivate(UUID id) {
        SubAssetType subAssetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
        subAssetType.setActive(false);
        return repositoryPort.save(subAssetType);
    }
}
