package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetStatusService {

    private final AssetStatusRepositoryPort repositoryPort;

    public AssetStatus create(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un estado con el código: " + code);
        }
        AssetStatus assetStatus = new AssetStatus(null, code, name);
        return repositoryPort.save(assetStatus);
    }

    public AssetStatus findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));
    }

    public List<AssetStatus> findAll() {
        return repositoryPort.findAll();
    }

    public AssetStatus update(UUID id, String code, String name) {
        AssetStatus existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro estado con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        return repositoryPort.save(existing);
    }

    public void delete(UUID id) {
        AssetStatus existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de activo no encontrado con id: " + id));
        System.out.println("Eliminando estado con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}