package com.datacenter.asset.infrastructure.adapters.subasset.in.rest.controller;

import com.datacenter.asset.domain.configuration.SubAssetType;
import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.fieldDefinition.in.FieldDefinitionUseCase;
import com.datacenter.asset.domain.ports.fieldDefinition.in.FieldGroupUseCase;
import com.datacenter.asset.domain.ports.subasset.in.ManageSubAssetTypeUseCase;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.response.FieldDefinitionResponse;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.response.FieldGroupFormResponse;
import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.mapper.FieldDefinitionRestMapper;
import com.datacenter.asset.infrastructure.adapters.subasset.in.rest.dto.request.CreateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.subasset.in.rest.dto.request.UpdateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.subasset.in.rest.dto.response.SubAssetTypeFormResponse;
import com.datacenter.asset.infrastructure.adapters.subasset.in.rest.dto.response.SubAssetTypeResponse;
import com.datacenter.asset.infrastructure.adapters.subasset.in.rest.mapper.SubAssetTypeRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sub-asset-types")
public class SubAssetTypeController {

    private final ManageSubAssetTypeUseCase useCase;
    private final FieldGroupUseCase fieldGroupUseCase;
    private final FieldDefinitionUseCase fieldDefinitionUseCase;
    private final SubAssetTypeRestMapper mapper;
    private final FieldDefinitionRestMapper fieldDefinitionRestMapper;

    public SubAssetTypeController(
            ManageSubAssetTypeUseCase useCase,
            FieldGroupUseCase fieldGroupUseCase,
            FieldDefinitionUseCase fieldDefinitionUseCase,
            SubAssetTypeRestMapper mapper,
            FieldDefinitionRestMapper fieldDefinitionRestMapper
    ) {
        this.useCase = useCase;
        this.fieldGroupUseCase = fieldGroupUseCase;
        this.fieldDefinitionUseCase = fieldDefinitionUseCase;
        this.mapper = mapper;
        this.fieldDefinitionRestMapper = fieldDefinitionRestMapper;
    }

    @PostMapping
    public ResponseEntity<SubAssetTypeResponse> create(@Valid @RequestBody CreateSubAssetTypeRequest request) {
        SubAssetType domain = mapper.toDomain(request);
        SubAssetType saved = useCase.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<SubAssetTypeResponse>> findAll() {
        List<SubAssetTypeResponse> list = useCase.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubAssetTypeResponse> findById(@PathVariable UUID id) {
        SubAssetType domain = useCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(domain));
    }

    @GetMapping("/by-asset-type/{assetTypeId}")
    public ResponseEntity<List<SubAssetTypeResponse>> findByAssetTypeId(@PathVariable UUID assetTypeId) {
        List<SubAssetTypeResponse> list = useCase.findByAssetTypeId(assetTypeId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubAssetTypeResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateSubAssetTypeRequest request) {
        SubAssetType updated = useCase.update(id, request.getCode(), request.getName(), request.getDescription());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<SubAssetTypeResponse> activate(@PathVariable UUID id) {
        SubAssetType updated = useCase.activate(id);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<SubAssetTypeResponse> desactivate(@PathVariable UUID id) {
        SubAssetType updated = useCase.desactivate(id);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @GetMapping("/{id}/form")
    public ResponseEntity<SubAssetTypeFormResponse> getFormBySubAssetType(@PathVariable UUID id) {
        SubAssetType subAssetType = useCase.findById(id);

        List<FieldGroup> rawGroups = fieldGroupUseCase.findBySubAssetTypeId(id);
        List<FieldDefinition> rawDefinitions = fieldDefinitionUseCase.findBySubAssetTypeId(id);

        Map<UUID, List<FieldDefinitionResponse>> fieldsByGroupId = rawDefinitions.stream()
                .filter(field -> field.getFieldGroupId() != null)
                .map(fieldDefinitionRestMapper::toResponse)
                .collect(Collectors.groupingBy(FieldDefinitionResponse::fieldGroupId));

        List<FieldGroupFormResponse> groups = rawGroups.stream()
                .map(group -> new FieldGroupFormResponse(
                        group.getId(),
                        group.getName(),
                        group.getDisplayOrder(),
                        fieldsByGroupId.getOrDefault(group.getId(), List.of())
                ))
                .toList();

        SubAssetTypeFormResponse response = new SubAssetTypeFormResponse(
                id,
                subAssetType.getName(),
                groups
        );

        return ResponseEntity.ok(response);
    }
}