package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.in.FieldDefinitionUseCase;
import com.datacenter.asset.domain.ports.out.FieldDefinitionRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.FieldDefinitionRestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldDefinitionService implements FieldDefinitionUseCase {

    private final FieldDefinitionRepositoryPort repository;
    private final FieldDefinitionRestMapper mapper;

    public FieldDefinitionService(
            FieldDefinitionRepositoryPort repository,
            FieldDefinitionRestMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FieldDefinitionResponse create(CreateFieldDefinitionRequest request) {

        FieldDefinition fieldDefinition = new FieldDefinition();

        fieldDefinition.setSubAssetTypeId(request.getSubAssetTypeId());
        fieldDefinition.setFieldGroupId(request.getFieldGroupId());
        fieldDefinition.setName(request.getName());
        fieldDefinition.setLabel(request.getLabel());
        fieldDefinition.setFieldType(request.getFieldType());
        fieldDefinition.setRequired(Boolean.TRUE.equals(request.getRequired()));
        fieldDefinition.setVisible(request.getVisible() == null || request.getVisible());
        fieldDefinition.setEditable(request.getEditable() == null || request.getEditable());
        fieldDefinition.setUnique(Boolean.TRUE.equals(request.getUnique()));
        fieldDefinition.setMaxLength(request.getMaxLength());
        fieldDefinition.setDisplayOrder(request.getDisplayOrder());
        fieldDefinition.setDefaultValue(request.getDefaultValue());
        fieldDefinition.setActive(true);

        return mapper.toResponse(repository.save(fieldDefinition));
    }

    @Override
    public List<FieldDefinitionResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FieldDefinitionResponse findById(UUID id) {

        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field definition not found: " + id));

        return mapper.toResponse(fieldDefinition);
    }

    @Override
    public List<FieldDefinitionResponse> findBySubAssetTypeId(UUID subAssetTypeId) {

        return repository.findBySubAssetTypeId(subAssetTypeId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<FieldDefinitionResponse> findByFieldGroupId(UUID fieldGroupId) {

        return repository.findByFieldGroupId(fieldGroupId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FieldDefinitionResponse update(
            UUID id,
            UpdateFieldDefinitionRequest request
    ) {

        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field definition not found: " + id));

        fieldDefinition.setFieldGroupId(request.getFieldGroupId());
        fieldDefinition.setName(request.getName());
        fieldDefinition.setLabel(request.getLabel());
        fieldDefinition.setFieldType(request.getFieldType());
        fieldDefinition.setRequired(Boolean.TRUE.equals(request.getRequired()));
        fieldDefinition.setVisible(Boolean.TRUE.equals(request.getVisible()));
        fieldDefinition.setEditable(Boolean.TRUE.equals(request.getEditable()));
        fieldDefinition.setUnique(Boolean.TRUE.equals(request.getUnique()));
        fieldDefinition.setMaxLength(request.getMaxLength());
        fieldDefinition.setDisplayOrder(request.getDisplayOrder());
        fieldDefinition.setDefaultValue(request.getDefaultValue());

        return mapper.toResponse(repository.update(fieldDefinition));
    }

    @Override
    public void activate(UUID id) {

        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field definition not found: " + id));

        fieldDefinition.setActive(true);

        repository.update(fieldDefinition);
    }

    @Override
    public void deactivate(UUID id) {

        FieldDefinition fieldDefinition = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field definition not found: " + id));

        fieldDefinition.setActive(false);

        repository.update(fieldDefinition);
    }
}