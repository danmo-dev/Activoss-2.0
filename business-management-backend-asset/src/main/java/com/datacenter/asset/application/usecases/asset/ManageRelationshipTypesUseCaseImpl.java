package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.in.asset.ManageRelationshipTypesUseCase;
import com.datacenter.asset.domain.ports.out.asset.RelationshipTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageRelationshipTypesUseCaseImpl implements ManageRelationshipTypesUseCase {

    private final RelationshipTypeRepositoryPort repositoryPort;

    @Override
    @Transactional
    public AssetRelationshipType createAssetRelationshipType(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe un tipo de relación con el código: " + code);
        }
        AssetRelationshipType relationshipType = new AssetRelationshipType(null, code, name);
        return repositoryPort.save(relationshipType);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetRelationshipType getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de relación no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRelationshipType> getAllAssetRelationshipTypes() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional
    public AssetRelationshipType update(UUID id, String code, String name) {
        AssetRelationshipType existing = getById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe otro tipo de relación con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);
        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AssetRelationshipType existing = getById(id);
        repositoryPort.deleteById(existing.getId());
    }
}