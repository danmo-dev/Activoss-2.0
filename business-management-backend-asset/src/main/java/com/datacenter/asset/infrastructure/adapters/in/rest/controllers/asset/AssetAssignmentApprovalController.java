package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.AssignAssetUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssetAssignmentApprovalController {

    private final AssignAssetUseCase assignAssetUseCase;

    public AssetAssignmentApprovalController(AssignAssetUseCase assignAssetUseCase) {
        this.assignAssetUseCase = assignAssetUseCase;
    }

    // POST: Devuelve el link del acta ya generada
    @PostMapping("/{id}/acta")
    public ResponseEntity<String> generarActa(@PathVariable UUID id) {
        AssetAssignment assignment = assignAssetUseCase.findById(id); // <-- solo buscar

        if (assignment.getState() != AssignmentState.ACCEPTED) {
            throw new IllegalStateException("El acta solo existe si la asignación fue aceptada.");
        }

        String fullUrl = "http://localhost:8080" + assignment.getPdfPath();
        return ResponseEntity.ok(fullUrl);
    }
}
