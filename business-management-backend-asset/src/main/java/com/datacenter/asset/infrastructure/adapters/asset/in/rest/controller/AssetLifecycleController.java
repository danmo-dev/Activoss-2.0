package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.ports.asset.in.ManageAssetLifecycleUseCase;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request.ChangeStateRequest;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.response.AssetHistoryResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assets/{assetId}/lifecycle")
@RequiredArgsConstructor
public class AssetLifecycleController {

    private final ManageAssetLifecycleUseCase lifecycleUseCase;

    @PostMapping("/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID assetId, @RequestBody ChangeStateRequest request) {
        lifecycleUseCase.deactivateAsset(assetId, request.getReason(), request.getExecutedBy());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID assetId, @RequestBody ChangeStateRequest request) {
        lifecycleUseCase.reactivateAsset(assetId, request.getReason(), request.getExecutedBy());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/decommission")
    public ResponseEntity<Void> decommission(@PathVariable UUID assetId, @RequestBody ChangeStateRequest request) {
        lifecycleUseCase.decommissionAsset(assetId, request.getReason(), request.getExecutedBy());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<AssetHistoryResponse>> getHistory(@PathVariable UUID assetId) {
        var history = lifecycleUseCase.getAssetHistory(assetId).stream()
                .map(h -> AssetHistoryResponse.builder()
                        .id(h.getId())
                        .assetId(h.getAssetId())
                        .eventDate(h.getEventDate())
                        .eventType(h.getEventType())
                        .executedBy(h.getExecutedBy())
                        .description(h.getDescription())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }
}