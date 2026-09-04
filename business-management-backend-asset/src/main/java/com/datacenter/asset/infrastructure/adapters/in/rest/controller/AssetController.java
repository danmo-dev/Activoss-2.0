package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.application.service.AssetService;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@RequestBody CreateAssetRequest request) {
        AssetResponse createdAsset = assetService.createAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAllAssets() {
        List<AssetResponse> assets = assetService.findAll(); // Ajustado a findAll()
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable UUID id) {
        AssetResponse asset = assetService.findById(id);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<AssetResponse> getAssetByCode(
            @PathVariable String code
    ) {

        AssetResponse asset =
                assetService.findByCode(code);

        return ResponseEntity.ok(asset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAssetRequest request
    ) {

        AssetResponse updatedAsset =
                assetService.updateAsset(id, request);

        return ResponseEntity.ok(updatedAsset);
    }
}