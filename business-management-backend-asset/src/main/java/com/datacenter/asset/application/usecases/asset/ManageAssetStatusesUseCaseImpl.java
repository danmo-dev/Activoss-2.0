package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetStatusesUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetStatusesUseCaseImpl implements ManageAssetStatusesUseCase {

    private final AssetStatusRepositoryPort repositoryPort;

    @Override
    @Transactional
    public AssetStatus createAssetStatus(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe un estado con el código: " + code);
        }
        AssetStatus assetStatus = new AssetStatus(null, code, name);
        return repositoryPort.save(assetStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetStatus getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de activo no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetStatus> getAllAssetStatuses() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional
    public AssetStatus update(UUID id, String code, String name) {
        AssetStatus existing = getById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe otro estado con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AssetStatus existing = getById(id);
        repositoryPort.deleteById(existing.getId());
    }
}