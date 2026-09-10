package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetUseCase;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request.CreateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request.UpdateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.response.AssetResponse;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.mapper.AssetRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final ManageAssetUseCase useCase;
    private final AssetRestMapper restMapper;

    public AssetController(ManageAssetUseCase useCase, AssetRestMapper restMapper) {
        this.useCase = useCase;
        this.restMapper = restMapper;
    }

    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@RequestBody CreateAssetRequest request) {
        Asset domainAsset = restMapper.toDomain(request);
        Asset createdAsset = useCase.createAsset(domainAsset);
        return ResponseEntity.status(HttpStatus.CREATED).body(restMapper.toResponse(createdAsset));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAllAssets() {
        List<AssetResponse> assets = useCase.findAll().stream()
                .map(restMapper::toResponse)
                .toList();
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable UUID id) {
        Asset asset = useCase.findById(id);
        return ResponseEntity.ok(restMapper.toResponse(asset));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<AssetResponse> getAssetByCode(@PathVariable String code) {
        Asset asset = useCase.findByCode(code);
        return ResponseEntity.ok(restMapper.toResponse(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAssetRequest request
    ) {
        Asset updatedAsset = useCase.updateAsset(
                id, request.getCompanyId(), request.getAssetTypeId(), request.getSubAssetTypeId(),
                request.getOwnershipTypeId(), request.getAssetStatusId(), request.getLocationId(), request.getOwnerId(),
                request.getCode(), request.getName(), request.getDescription(), request.getRegistrationDate()
        );
        return ResponseEntity.ok(restMapper.toResponse(updatedAsset));
    }
}