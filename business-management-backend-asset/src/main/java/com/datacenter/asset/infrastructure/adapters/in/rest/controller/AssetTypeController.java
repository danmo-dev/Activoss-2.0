package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.application.service.AssetTypeService;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetTypeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/asset-types")
public class AssetTypeController {

    private final AssetTypeService service;

    public AssetTypeController(AssetTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AssetTypeResponse> create(
            @Valid @RequestBody CreateAssetTypeRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AssetTypeResponse>> findAll() {

        return ResponseEntity.ok(
                service.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetTypeResponse> findById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetTypeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAssetTypeRequest request
    ) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AssetTypeResponse> activate(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.activate(id)
        );
    }

        @PatchMapping("/{id}/deactivate")
        public ResponseEntity<AssetTypeResponse> deactivate(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.deactivate(id)
        );
    }
}