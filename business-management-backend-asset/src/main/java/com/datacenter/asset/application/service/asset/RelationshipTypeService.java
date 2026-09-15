package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.out.asset.RelationshipTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationshipTypeService {

    private final RelationshipTypeRepositoryPort repositoryPort;

    public AssetRelationshipType create(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un tipo de relación con el código: " + code);
        }
        AssetRelationshipType relationshipType = new AssetRelationshipType(null, code, name);
        return repositoryPort.save(relationshipType);
    }

    public AssetRelationshipType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));
    }

    public List<AssetRelationshipType> findAll() {
        return repositoryPort.findAll();
    }

    public AssetRelationshipType update(UUID id, String code, String name) {
        AssetRelationshipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro tipo de relación con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        return repositoryPort.save(existing);
    }

    public void delete(UUID id) {
        AssetRelationshipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));
        System.out.println("Eliminando tipo de relación con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}
