package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.in.subasset.ManageSubAssetTypeUseCase;
import com.datacenter.asset.domain.ports.out.subassettype.SubAssetTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageSubAssetTypeUseCaseImpl implements ManageSubAssetTypeUseCase {

    private final SubAssetTypeRepositoryPort repositoryPort;

    @Override
    @Transactional
    public SubAssetType create(SubAssetType subAssetType) {
        if (repositoryPort.existsByCode(subAssetType.getCode())) {
            throw new BusinessException("El subtipo de activo ya existe con el código: " + subAssetType.getCode());
        }
        return repositoryPort.save(subAssetType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubAssetType> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubAssetType> findByAssetTypeId(UUID assetTypeId) {
        return repositoryPort.findByAssetTypeId(assetTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public SubAssetType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subtipo de activo no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public SubAssetType update(UUID id, String code, String name, String description) {
        SubAssetType existing = findById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new BusinessException("El subtipo de activo ya existe con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        existing.setDescription(description);

        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public SubAssetType activate(UUID id) {
        SubAssetType subAssetType = findById(id);
        subAssetType.setActive(true);
        return repositoryPort.save(subAssetType);
    }

    @Override
    @Transactional
    public SubAssetType desactivate(UUID id) {
        SubAssetType subAssetType = findById(id);
        subAssetType.setActive(false);
        return repositoryPort.save(subAssetType);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SubAssetType existing = findById(id);
        repositoryPort.deleteById(existing.getId());
    }
}