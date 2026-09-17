package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.ports.in.asset.SaveAssetValuesUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.SaveAssetValuesRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.UpdateAssetValueRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssetValueResponse;

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
    public ResponseEntity<List<AssetValueResponse>> getValues(
            @PathVariable UUID assetId) {

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

    @PutMapping("/{id}")
    public ResponseEntity<AssetValueResponse> update(
            @PathVariable UUID assetId,
            @PathVariable UUID id,
            @RequestBody UpdateAssetValueRequest request) {

        AssetValue updated = useCase.update(
                id,
                request.getFieldDefinitionId(),
                request.getValue()
        );

        AssetValueResponse response = AssetValueResponse.builder()
                .id(updated.getId())
                .assetId(updated.getAssetId())
                .fieldDefinitionId(updated.getFieldDefinitionId())
                .value(updated.getValue())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID assetId,
            @PathVariable UUID id) {

        useCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}