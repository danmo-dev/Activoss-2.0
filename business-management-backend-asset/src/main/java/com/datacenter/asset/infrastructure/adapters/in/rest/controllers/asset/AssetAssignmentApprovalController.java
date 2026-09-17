package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.ports.in.asset.AssignAssetUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssetAssignmentApprovalController {

    private final AssignAssetUseCase assignAssetUseCase;

    public AssetAssignmentApprovalController(AssignAssetUseCase assignAssetUseCase) {
        this.assignAssetUseCase = assignAssetUseCase;
    }

    @PostMapping("/{id}/acta")
    public ResponseEntity<Map<String, String>> generarActa(
            @PathVariable UUID id, 
            @RequestBody(required = false) Map<String, String> requestBody) {
        
        if (requestBody == null || !requestBody.containsKey("deliveredById") || requestBody.get("deliveredById").isBlank()) {
            throw new IllegalArgumentException("El body es obligatorio y debe contener el 'deliveredById'.");
        }
        
        UUID deliveredById = UUID.fromString(requestBody.get("deliveredById"));
        String observaciones = requestBody.getOrDefault("observaciones", "");

        String fullUrl = assignAssetUseCase.generarActa(id, deliveredById, observaciones);
        
        // Retornamos un JSON estándar que cualquier cliente (Postman/Angular) puede leer sin error
        return ResponseEntity.ok(Map.of("url", fullUrl));
    }
}