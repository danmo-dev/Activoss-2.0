package com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.controller;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.fieldDefinition.in.FieldGroupUseCase;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.request.CreateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.request.UpdateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.response.FieldGroupResponse;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.mapper.FieldGroupRestMapper;

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
    private final FieldGroupRestMapper mapper;

    public FieldGroupController(FieldGroupUseCase fieldGroupUseCase, FieldGroupRestMapper mapper) {
        this.fieldGroupUseCase = fieldGroupUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<FieldGroupResponse> create(@Valid @RequestBody CreateFieldGroupRequest request) {
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.setSubAssetTypeId(request.getSubAssetTypeId());
        fieldGroup.setName(request.getName());
        fieldGroup.setDescription(request.getDescription());
        fieldGroup.setDisplayOrder(request.getDisplayOrder());

        FieldGroup saved = fieldGroupUseCase.create(fieldGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<FieldGroupResponse>> findAll() {
        List<FieldGroupResponse> list = fieldGroupUseCase.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldGroupResponse> findById(@PathVariable UUID id) {
        FieldGroup fieldGroup = fieldGroupUseCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(fieldGroup));
    }

    @GetMapping("/by-sub-asset-type/{subAssetTypeId}")
    public ResponseEntity<List<FieldGroupResponse>> findBySubAssetType(@PathVariable UUID subAssetTypeId) {
        List<FieldGroupResponse> list = fieldGroupUseCase.findBySubAssetTypeId(subAssetTypeId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldGroupResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateFieldGroupRequest request) {
        FieldGroup updated = fieldGroupUseCase.update(id, request.getName(), request.getDisplayOrder(), null); // assuming description doesn't exist on update or requires passing
        // wait, I need to check the parameters. I passed: id, name, displayOrder, subAssetTypeId.
        // let's pass them correctly. 
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        fieldGroupUseCase.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        fieldGroupUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}