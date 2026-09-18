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

    // Único endpoint para generar el acta. Soporta texto y archivo opcional mediante form-data.
    @PostMapping(value = "/{id}/acta", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> generarActaEndpoint(
            @PathVariable UUID id,
            @RequestParam("deliveredById") UUID deliveredById,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam(value = "imagen", required = false) org.springframework.web.multipart.MultipartFile imagen) {
        
        try {
            // Convierte el archivo a byte[] si existe, si no lo deja null
            byte[] imagenBytes = (imagen != null && !imagen.isEmpty()) ? imagen.getBytes() : null;
            
            // Pasamos un string vacío si observaciones viene null
            String obsFinales = (observaciones != null) ? observaciones : "";
            
            String pdfUrl = assignAssetUseCase.generarActa(id, deliveredById, obsFinales, imagenBytes);
            
            // Retornamos un JSON estructurado
            return ResponseEntity.ok(Map.of("url", pdfUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}