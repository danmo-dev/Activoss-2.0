package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetTypeUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.CreateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.UpdateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssetTypeResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset.AssetTypeRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/asset-types")
public class AssetTypeController {

    private final ManageAssetTypeUseCase useCase;
    private final AssetTypeRestMapper mapper;

    public AssetTypeController(ManageAssetTypeUseCase useCase, AssetTypeRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<AssetTypeResponse> create(@Valid @RequestBody CreateAssetTypeRequest request) {
        AssetType assetType = mapper.toDomain(request);
        AssetType saved = useCase.create(assetType);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AssetTypeResponse>> findAll() {
        List<AssetTypeResponse> list = useCase.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetTypeResponse> findById(@PathVariable UUID id) {
        AssetType assetType = useCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(assetType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetTypeResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateAssetTypeRequest request) {
        AssetType updated = useCase.update(id, request.getCode(), request.getName(), request.getDescription());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AssetTypeResponse> activate(@PathVariable UUID id) {
        AssetType updated = useCase.activate(id);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AssetTypeResponse> deactivate(@PathVariable UUID id) {
        AssetType updated = useCase.deactivate(id);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content cuando se elimina con éxito
    }
}