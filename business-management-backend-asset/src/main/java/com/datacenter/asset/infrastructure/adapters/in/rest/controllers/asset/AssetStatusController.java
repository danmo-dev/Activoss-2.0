package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetStatusesUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/asset-statuses")
public class AssetStatusController {

    private final ManageAssetStatusesUseCase useCase;

    public AssetStatusController(ManageAssetStatusesUseCase useCase) {
        this.useCase = useCase;
    }

    public record CreateAssetStatusRequest(String code, String name) {}
    public record UpdateAssetStatusRequest(String code, String name) {} // Record para Update

    @PostMapping
    public ResponseEntity<AssetStatus> create(@RequestBody CreateAssetStatusRequest request) {
        return ResponseEntity.ok(
                useCase.createAssetStatus(request.code(), request.name())
        );
    }

    @GetMapping
    public ResponseEntity<List<AssetStatus>> findAll() {
        return ResponseEntity.ok(useCase.getAllAssetStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetStatus> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetStatus> update(
            @PathVariable UUID id, 
            @RequestBody UpdateAssetStatusRequest request) {
        return ResponseEntity.ok(
                useCase.update(id, request.code(), request.name())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}