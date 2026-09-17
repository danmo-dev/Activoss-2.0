package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetAssignmentUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.AssignAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.AcceptAssignmentRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.TransferAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.ReturnAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssignmentResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset.AssetAssignmentRestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssetAssignmentController {

    private final ManageAssetAssignmentUseCase assignmentUseCase;
    private final AssetAssignmentRestMapper mapper;

    @PostMapping
    public ResponseEntity<AssignmentResponse> assignAsset(@Valid @RequestBody AssignAssetRequest request) {
        var domain = mapper.toDomain(request);
        // NUEVO: Pasamos el ID de la persona que está creando la asignación
        var created = assignmentUseCase.assignAsset(domain, request.getCreatedById());
        return ResponseEntity.status(201).body(mapper.toResponse(created));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<AssignmentResponse> acceptAssignment(
            @PathVariable UUID id,
            @RequestBody AcceptAssignmentRequest request) {
        
        // NUEVO: Pasamos el ID de quien entregó el activo físicamente
        var accepted = assignmentUseCase.acceptAssignment(
                id, 
                request.getDeliveredById(),
                request.getObservaciones() != null ? request.getObservaciones() : ""
        );
        return ResponseEntity.ok(mapper.toResponse(accepted));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<AssignmentResponse> rejectAssignment(@PathVariable UUID id) {
        var rejected = assignmentUseCase.rejectAssignment(id);
        return ResponseEntity.ok(mapper.toResponse(rejected));
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<AssignmentResponse> transferAsset(
            @PathVariable UUID id,
            @Valid @RequestBody TransferAssetRequest request) {
        var transferred = assignmentUseCase.transferAsset(
                id, request.getDeliveredById(), request.getNewAssigneeId(), request.getTransferReason()
        );
        return ResponseEntity.ok(mapper.toResponse(transferred));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<AssignmentResponse> returnAsset(
            @PathVariable UUID id,
            @Valid @RequestBody ReturnAssetRequest request) {
        var returned = assignmentUseCase.returnAsset(
                id, request.getReturnedById(), request.getReturnReason()
        );
        return ResponseEntity.ok(mapper.toResponse(returned));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByState(@PathVariable AssignmentState state) {
        var assignments = assignmentUseCase.getAssignmentsByState(state);
        var response = assignments.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/asset/{assetId}/history")
    public ResponseEntity<?> getAssetHistory(@PathVariable UUID assetId) {
        var history = assignmentUseCase.getAssetHistory(assetId);
        return ResponseEntity.ok(history);
    }
}