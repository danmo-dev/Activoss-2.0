package com.datacenter.asset.application.usecases.fielddefinition;

import com.datacenter.asset.domain.models.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.in.fielddefinition.FieldDefinitionUseCase;
import com.datacenter.asset.application.service.fielddefinition.FieldDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FieldDefinitionUseCaseImpl implements FieldDefinitionUseCase {

    private final FieldDefinitionService fieldDefinitionService;

    @Override
    @Transactional
    public FieldDefinition create(FieldDefinition fieldDefinition) {
        return fieldDefinitionService.create(fieldDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findAll() {
        return fieldDefinitionService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FieldDefinition findById(UUID id) {
        return fieldDefinitionService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId) {
        return fieldDefinitionService.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId) {
        return fieldDefinitionService.findByFieldGroupId(fieldGroupId);
    }

    @Override
    @Transactional
    public FieldDefinition update(FieldDefinition fieldDefinition) {
        return fieldDefinitionService.update(fieldDefinition);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        fieldDefinitionService.activate(id);
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        fieldDefinitionService.deactivate(id);
    }
}