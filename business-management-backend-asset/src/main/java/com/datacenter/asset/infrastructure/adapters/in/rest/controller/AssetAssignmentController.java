package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.ports.in.AssignAssetUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.AssignAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssignmentResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.AssetAssignmentRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssetAssignmentController {

    private final AssignAssetUseCase assignAssetUseCase;
    private final AssetAssignmentRestMapper mapper;

    @PostMapping
    public ResponseEntity<AssignmentResponse> assignAsset(@RequestBody AssignAssetRequest request) {
        var domain = mapper.toDomain(request);
        var created = assignAssetUseCase.assignAsset(domain);
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<AssignmentResponse> acceptAssignment(@PathVariable UUID id) {
        var accepted = assignAssetUseCase.acceptAssignment(id);
        return ResponseEntity.ok(mapper.toResponse(accepted));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<AssignmentResponse> rejectAssignment(@PathVariable UUID id) {
        var rejected = assignAssetUseCase.rejectAssignment(id);
        return ResponseEntity.ok(mapper.toResponse(rejected));
    }
}