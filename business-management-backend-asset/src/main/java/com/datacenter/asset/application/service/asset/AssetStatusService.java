package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetStatusesUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetStatusService implements ManageAssetStatusesUseCase {

    private final AssetStatusRepositoryPort repositoryPort;

    public AssetStatusService(AssetStatusRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetStatus createAssetStatus(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un estado con el código: " + code);
        }

        AssetStatus assetStatus = new AssetStatus(
                null,
                code,
                name
        );

        return repositoryPort.save(assetStatus);
    }

    @Override
    public AssetStatus getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));
    }

    @Override
    public List<AssetStatus> getAllAssetStatuses() {
        return repositoryPort.findAll();
    }

    @Override
    public AssetStatus update(UUID id, String code, String name) {
        AssetStatus existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));

        // Validar que no estemos duplicando un código existente en OTRO registro diferente
        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro estado con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);

        return repositoryPort.save(existing);
    }

    @Override
    public void delete(UUID id) {
        AssetStatus existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));
        System.out.println("Eliminando estado con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}