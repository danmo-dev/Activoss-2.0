package com.datacenter.asset.application.service.fielddefinition;

import com.datacenter.asset.domain.models.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldDefinitionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldDefinitionService {

    private final FieldDefinitionRepositoryPort repository;

    public FieldDefinition create(FieldDefinition fieldDefinition) {
        fieldDefinition.setActive(true);
        return repository.save(fieldDefinition);
    }

    public List<FieldDefinition> findAll() {
        return repository.findAll();
    }

    public FieldDefinition findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field definition not found: " + id));
    }

    public List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    public List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId) {
        return repository.findByFieldGroupId(fieldGroupId);
    }

    public FieldDefinition update(FieldDefinition updatedFieldDefinition) {
        FieldDefinition fieldDefinition = findById(updatedFieldDefinition.getId());

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

    public void activate(UUID id) {
        FieldDefinition fieldDefinition = findById(id);
        fieldDefinition.setActive(true);
        repository.update(fieldDefinition);
    }

    public void deactivate(UUID id) {
        FieldDefinition fieldDefinition = findById(id);
        fieldDefinition.setActive(false);
        repository.update(fieldDefinition);
    }
}