package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.application.service.external.AssetAssignmentApprovalService;
import com.datacenter.asset.domain.assignment.AssetAssignment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssetAssignmentApprovalController {

    private final AssetAssignmentApprovalService approvalService;

    public AssetAssignmentApprovalController(AssetAssignmentApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // POST: Genera el acta y devuelve el link
    @PostMapping("/{id}/acta")
    public ResponseEntity<String> generarActa(@PathVariable UUID id) {
        AssetAssignment assignment = approvalService.approveAssignment(id);
        String fullUrl = "http://localhost:8080" + assignment.getPdfPath();
        return ResponseEntity.ok(fullUrl);
    }
}
