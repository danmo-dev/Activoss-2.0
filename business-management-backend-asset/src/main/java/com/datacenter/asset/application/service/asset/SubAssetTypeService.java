package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.out.subassettype.SubAssetTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubAssetTypeService {

    private final SubAssetTypeRepositoryPort repositoryPort;

    public SubAssetType create(SubAssetType subAssetType) {
        if (repositoryPort.existsByCode(subAssetType.getCode())) {
            throw new RuntimeException("Sub asset type already exists with code: " + subAssetType.getCode());
        }
        return repositoryPort.save(subAssetType);
    }

    public List<SubAssetType> findAll() {
        return repositoryPort.findAll();
    }

    public List<SubAssetType> findByAssetTypeId(UUID assetTypeId) {
        return repositoryPort.findByAssetTypeId(assetTypeId);
    }

    public SubAssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
    }

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

    public SubAssetType activate(UUID id) {
        SubAssetType subAssetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
        subAssetType.setActive(true);
        return repositoryPort.save(subAssetType);
    }

    public SubAssetType deactivate(UUID id) {
        SubAssetType subAssetType = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
        subAssetType.setActive(false);
        return repositoryPort.save(subAssetType);
    }

    public void delete(UUID id) {
        SubAssetType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub asset type not found with id: " + id));
        System.out.println("Eliminando sub asset type con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}
