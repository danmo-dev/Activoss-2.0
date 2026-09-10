package com.datacenter.asset.application.service.fielddefinition;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.fieldDefinition.in.FieldDefinitionUseCase;
import com.datacenter.asset.domain.ports.fieldDefinition.out.FieldDefinitionRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldDefinitionService implements FieldDefinitionUseCase {

    private final FieldDefinitionRepositoryPort repository;

    public FieldDefinitionService(FieldDefinitionRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public FieldDefinition create(FieldDefinition fieldDefinition) {
        fieldDefinition.setActive(true);
        return repository.save(fieldDefinition);
    }

    @Override
    public List<FieldDefinition> findAll() {
        return repository.findAll();
    }

    @Override
    public FieldDefinition findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field definition not found: " + id));
    }

    @Override
    public List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    public List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId) {
        return repository.findByFieldGroupId(fieldGroupId);
    }

    @Override
    public FieldDefinition update(FieldDefinition updatedFieldDefinition) {
        FieldDefinition fieldDefinition = repository.findById(updatedFieldDefinition.getId())
                .orElseThrow(() -> new RuntimeException("Field definition not found: " + updatedFieldDefinition.getId()));

        fieldDefinition.setFieldGroupId(updatedFieldDefinition.getFieldGroupId());
        fieldDefinition.setName(updatedFieldDefinition.getName());
        fieldDefinition.setLabel(updatedFieldDefinition.getLabel());
        fieldDefinition.setFieldType(updatedFieldDefinition.getFieldType());
        fieldDefinition.setRequired(updatedFieldDefinition.getRequired());
        fieldDefinition.setVisible(updatedFieldDefinition.getVisible());
        fieldDefinition.setEditable(updatedFieldDefinition.getEditable());
        fieldDefinition.setUnique(updatedFieldDefinition.getUnique());
        fieldDefinition.setMaxLength(updatedFieldDefinition.getMaxLength());
        fieldDefinition.setDisplayOrder(updatedFieldDefinition.getDisplayOrder());
        fieldDefinition.setDefaultValue(updatedFieldDefinition.getDefaultValue());
        
        return repository.update(fieldDefinition);
    }

    @Override
    public void activate(UUID id) {
        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field definition not found: " + id));
        fieldDefinition.setActive(true);
        repository.update(fieldDefinition);
    }

    @Override
    public void deactivate(UUID id) {
        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field definition not found: " + id));
        fieldDefinition.setActive(false);
        repository.update(fieldDefinition);
    }
}
