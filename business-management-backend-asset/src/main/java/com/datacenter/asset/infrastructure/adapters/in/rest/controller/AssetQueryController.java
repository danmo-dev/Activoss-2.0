package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.AssetRestMapper;
import com.datacenter.asset.domain.ports.in.SearchAssetsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assets/query")
public class AssetQueryController {

    private final SearchAssetsUseCase searchAssetsUseCase;
    private final AssetRestMapper assetRestMapper;

    public AssetQueryController(SearchAssetsUseCase searchAssetsUseCase, AssetRestMapper assetRestMapper) {
        this.searchAssetsUseCase = searchAssetsUseCase;
        this.assetRestMapper = assetRestMapper;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AssetResponse>> searchAssets(
            @RequestParam(required = false) UUID typeId,
            @RequestParam(required = false) UUID statusId,
            @RequestParam(required = false) UUID locationId,
            @RequestParam(required = false) String keyword) {

        var assets = searchAssetsUseCase.searchWithFilters(typeId, statusId, locationId, keyword);
        return ResponseEntity.ok(assets.stream().map(assetRestMapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/my-assets/{personId}")
    public ResponseEntity<List<AssetResponse>> getMyAssets(@PathVariable UUID personId) {
        var assets = searchAssetsUseCase.getMyAssignedAssets(personId);
        return ResponseEntity.ok(assets.stream().map(assetRestMapper::toResponse).collect(Collectors.toList()));
    }
}