package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.ports.in.FieldDefinitionUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;
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

    public FieldDefinitionController(
            FieldDefinitionUseCase fieldDefinitionUseCase
    ) {
        this.fieldDefinitionUseCase = fieldDefinitionUseCase;
    }

    @PostMapping
    public ResponseEntity<FieldDefinitionResponse> create(
            @Valid @RequestBody CreateFieldDefinitionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fieldDefinitionUseCase.create(request));
    }

    @GetMapping
    public ResponseEntity<List<FieldDefinitionResponse>> findAll() {
        return ResponseEntity.ok(fieldDefinitionUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDefinitionResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                fieldDefinitionUseCase.findById(id)
        );
    }

    @GetMapping("/by-sub-asset-type/{subAssetTypeId}")
    public ResponseEntity<List<FieldDefinitionResponse>> findBySubAssetType(
            @PathVariable UUID subAssetTypeId
    ) {
        return ResponseEntity.ok(
                fieldDefinitionUseCase.findBySubAssetTypeId(subAssetTypeId)
        );
    }

    @GetMapping("/by-field-group/{fieldGroupId}")
    public ResponseEntity<List<FieldDefinitionResponse>> findByFieldGroup(
            @PathVariable UUID fieldGroupId
    ) {
        return ResponseEntity.ok(
                fieldDefinitionUseCase.findByFieldGroupId(fieldGroupId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDefinitionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFieldDefinitionRequest request
    ) {
        return ResponseEntity.ok(
                fieldDefinitionUseCase.update(id, request)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID id
    ) {
        fieldDefinitionUseCase.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id
    ) {
        fieldDefinitionUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}