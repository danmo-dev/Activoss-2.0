package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.application.service.SubAssetTypeService;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.SubAssetTypeFormResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.SubAssetTypeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sub-asset-types")
public class SubAssetTypeController {

    private final SubAssetTypeService service;

    public SubAssetTypeController(
            SubAssetTypeService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SubAssetTypeResponse> create(
            @Valid @RequestBody CreateSubAssetTypeRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SubAssetTypeResponse>> findAll() {

        return ResponseEntity.ok(
                service.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubAssetTypeResponse> findById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.findById(id)
        );
    }

    @GetMapping("/by-asset-type/{assetTypeId}")
    public ResponseEntity<List<SubAssetTypeResponse>>
    findByAssetTypeId(
            @PathVariable UUID assetTypeId
    ) {

        return ResponseEntity.ok(
                service.findByAssetTypeId(assetTypeId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubAssetTypeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSubAssetTypeRequest request
    ) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<SubAssetTypeResponse> activate(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.activate(id)
        );
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<SubAssetTypeResponse> desactivate(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.desactivate(id)
        );
    }

    @GetMapping("/{id}/form")
    public ResponseEntity<SubAssetTypeFormResponse> getFormBySubAssetType(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                service.getFormBySubAssetTypeId(id)
        );
    }
}