package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.in.ApproveAssignmentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssetAssignmentApprovalController {

    private final ApproveAssignmentUseCase approveAssignmentUseCase;

    public AssetAssignmentApprovalController(ApproveAssignmentUseCase approveAssignmentUseCase) {
        this.approveAssignmentUseCase = approveAssignmentUseCase;
    }

    @PostMapping("/{id}/acceptpdf")
    public ResponseEntity<AssetAssignment> acceptAssignment(@PathVariable UUID id) {
        return ResponseEntity.ok(approveAssignmentUseCase.acceptAssignment(id));
    }

    @PostMapping("/{id}/rejectpdf")
    public ResponseEntity<AssetAssignment> rejectAssignment(@PathVariable UUID id, @RequestBody Map<String, String> payload) {
        String reason = payload.getOrDefault("reason", "Sin justificación");
        return ResponseEntity.ok(approveAssignmentUseCase.rejectAssignment(id, reason));
    }
}