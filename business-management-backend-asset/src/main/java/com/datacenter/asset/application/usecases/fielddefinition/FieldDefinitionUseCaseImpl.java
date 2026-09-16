package com.datacenter.asset.application.usecases.fielddefinition;

import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.in.fielddefinition.FieldDefinitionUseCase;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldDefinitionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FieldDefinitionUseCaseImpl implements FieldDefinitionUseCase {

    private final FieldDefinitionRepositoryPort repository;

    @Override
    @Transactional
    public FieldDefinition create(FieldDefinition fieldDefinition) {
        fieldDefinition.setActive(true);
        return repository.save(fieldDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FieldDefinition findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Definición de campo no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId) {
        return repository.findByFieldGroupId(fieldGroupId);
    }

    @Override
    @Transactional
    public FieldDefinition update(FieldDefinition updated) {
        FieldDefinition existing = findById(updated.getId());
        existing.setFieldGroupId(updated.getFieldGroupId());
        existing.setName(updated.getName());
        existing.setLabel(updated.getLabel());
        existing.setFieldType(updated.getFieldType());
        existing.setRequired(updated.getRequired());
        existing.setVisible(updated.getVisible());
        existing.setEditable(updated.getEditable());
        existing.setUnique(updated.getUnique());
        existing.setMaxLength(updated.getMaxLength());
        existing.setDisplayOrder(updated.getDisplayOrder());
        existing.setDefaultValue(updated.getDefaultValue());
        return repository.update(existing);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        FieldDefinition existing = findById(id);
        existing.setActive(true);
        repository.update(existing);
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        FieldDefinition existing = findById(id);
        existing.setActive(false);
        repository.update(existing);
    }
}