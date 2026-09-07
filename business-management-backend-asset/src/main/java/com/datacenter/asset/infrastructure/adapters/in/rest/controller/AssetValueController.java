package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.asset.AssetValue;
import com.datacenter.asset.domain.ports.in.SaveAssetValuesUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.SaveAssetValuesRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetValueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assets/{assetId}/values")
@RequiredArgsConstructor
public class AssetValueController {

    private final SaveAssetValuesUseCase useCase;

    @PostMapping
    public ResponseEntity<List<AssetValueResponse>> saveValues(
            @PathVariable UUID assetId,
            @RequestBody SaveAssetValuesRequest request) {
        
        List<AssetValue> domainValues = request.getValues().stream()
                .map(item -> AssetValue.builder()
                        .fieldDefinitionId(item.getFieldDefinitionId())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());

        List<AssetValue> saved = useCase.saveAssetValues(assetId, domainValues);

        List<AssetValueResponse> response = saved.stream()
                .map(v -> AssetValueResponse.builder()
                        .id(v.getId())
                        .assetId(v.getAssetId())
                        .fieldDefinitionId(v.getFieldDefinitionId())
                        .value(v.getValue())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AssetValueResponse>> getValues(@PathVariable UUID assetId) {
        List<AssetValue> found = useCase.getAssetValues(assetId);
        List<AssetValueResponse> response = found.stream()
                .map(v -> AssetValueResponse.builder()
                        .id(v.getId())
                        .assetId(v.getAssetId())
                        .fieldDefinitionId(v.getFieldDefinitionId())
                        .value(v.getValue())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}