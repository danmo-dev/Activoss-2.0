package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.ports.in.FieldGroupUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/field-groups")
public class FieldGroupController {

    private final FieldGroupUseCase fieldGroupUseCase;

    public FieldGroupController(FieldGroupUseCase fieldGroupUseCase) {
        this.fieldGroupUseCase = fieldGroupUseCase;
    }

    @PostMapping
    public ResponseEntity<FieldGroupResponse> create(
            @Valid @RequestBody CreateFieldGroupRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fieldGroupUseCase.create(request));
    }

    @GetMapping
    public ResponseEntity<List<FieldGroupResponse>> findAll() {
        return ResponseEntity.ok(fieldGroupUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldGroupResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(fieldGroupUseCase.findById(id));
    }

    @GetMapping("/by-sub-asset-type/{subAssetTypeId}")
    public ResponseEntity<List<FieldGroupResponse>> findBySubAssetType(
            @PathVariable UUID subAssetTypeId
    ) {
        return ResponseEntity.ok(
                fieldGroupUseCase.findBySubAssetTypeId(subAssetTypeId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldGroupResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFieldGroupRequest request
    ) {
        return ResponseEntity.ok(
                fieldGroupUseCase.update(id, request)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID id
    ) {
        fieldGroupUseCase.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id
    ) {
        fieldGroupUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}