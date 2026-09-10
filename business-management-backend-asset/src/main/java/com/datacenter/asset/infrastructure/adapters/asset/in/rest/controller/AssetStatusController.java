package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetStatusesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asset-statuses")
public class AssetStatusController {

    private final ManageAssetStatusesUseCase useCase;

    public AssetStatusController(
            ManageAssetStatusesUseCase useCase
    ) {
        this.useCase = useCase;
    }

    public record CreateAssetStatusRequest(
            String code,
            String name
    ) {}

    @PostMapping
    public ResponseEntity<AssetStatus> create(
            @RequestBody CreateAssetStatusRequest request
    ) {
        return ResponseEntity.ok(
                useCase.createAssetStatus(
                        request.code(),
                        request.name()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AssetStatus>> findAll() {
        return ResponseEntity.ok(
                useCase.getAllAssetStatuses()
        );
    }
}