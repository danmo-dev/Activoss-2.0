package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.asset.in.ManageRelationshipTypesUseCase;
import com.datacenter.asset.domain.ports.asset.out.RelationshipTypeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RelationshipTypeService implements ManageRelationshipTypesUseCase {

    private final RelationshipTypeRepositoryPort repositoryPort;

    public RelationshipTypeService(RelationshipTypeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetRelationshipType createAssetRelationshipType(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un tipo de relación con el código: " + code);
        }

        AssetRelationshipType relationshipType = new AssetRelationshipType(
                null,
                code,
                name
        );

        return repositoryPort.save(relationshipType);
    }

    @Override
    public AssetRelationshipType getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));
    }

    @Override
    public List<AssetRelationshipType> getAllAssetRelationshipTypes() {
        return repositoryPort.findAll();
    }

    @Override
    public AssetRelationshipType update(UUID id, String code, String name) {
        AssetRelationshipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));

        // Validar que no exista OTRO registro con el mismo código
        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro tipo de relación con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);

        return repositoryPort.save(existing);
    }

    @Override
    public void delete(UUID id) {
        AssetRelationshipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de relación no encontrado con id: " + id));
        System.out.println("Eliminando estado con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}