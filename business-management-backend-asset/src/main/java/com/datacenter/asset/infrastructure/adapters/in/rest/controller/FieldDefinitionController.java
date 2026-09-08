package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.in.FieldDefinitionUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.FieldDefinitionRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/field-definitions")
public class FieldDefinitionController {

    private final FieldDefinitionUseCase fieldDefinitionUseCase;
    private final FieldDefinitionRestMapper mapper;

    public FieldDefinitionController(FieldDefinitionUseCase fieldDefinitionUseCase, FieldDefinitionRestMapper mapper) {
        this.fieldDefinitionUseCase = fieldDefinitionUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<FieldDefinitionResponse> create(@Valid @RequestBody CreateFieldDefinitionRequest request) {
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

        FieldDefinition saved = fieldDefinitionUseCase.create(fieldDefinition);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<FieldDefinitionResponse>> findAll() {
        List<FieldDefinitionResponse> list = fieldDefinitionUseCase.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDefinitionResponse> findById(@PathVariable UUID id) {
        FieldDefinition fieldDefinition = fieldDefinitionUseCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(fieldDefinition));
    }

    @GetMapping("/by-sub-asset-type/{subAssetTypeId}")
    public ResponseEntity<List<FieldDefinitionResponse>> findBySubAssetType(@PathVariable UUID subAssetTypeId) {
        List<FieldDefinitionResponse> list = fieldDefinitionUseCase.findBySubAssetTypeId(subAssetTypeId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-field-group/{fieldGroupId}")
    public ResponseEntity<List<FieldDefinitionResponse>> findByFieldGroup(@PathVariable UUID fieldGroupId) {
        List<FieldDefinitionResponse> list = fieldDefinitionUseCase.findByFieldGroupId(fieldGroupId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDefinitionResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateFieldDefinitionRequest request) {
        FieldDefinition fieldDefinition = new FieldDefinition();
        fieldDefinition.setId(id);
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

        FieldDefinition updated = fieldDefinitionUseCase.update(fieldDefinition);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        fieldDefinitionUseCase.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        fieldDefinitionUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}