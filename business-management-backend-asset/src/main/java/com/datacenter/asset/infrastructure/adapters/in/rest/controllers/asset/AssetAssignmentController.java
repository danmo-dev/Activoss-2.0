package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetAssignmentUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.AssignAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssignmentResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset.AssetAssignmentRestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        var created = assignmentUseCase.assignAsset(domain, request.getCreatedById());
        return ResponseEntity.status(201).body(mapper.toResponse(created));
    }

    // <-- 1. ACEPTAR: Genera Acta -->
    @PostMapping(value = "/{id}/accept", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentResponse> acceptAssignment(
            @PathVariable UUID id,
            @RequestParam("deliveredById") UUID deliveredById,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        try {
            byte[] imagenBytes = (imagen != null && !imagen.isEmpty()) ? imagen.getBytes() : null;
            String obsFinales = (observaciones != null) ? observaciones : "";
            
            AssetAssignment accepted = assignmentUseCase.acceptAssignment(id, deliveredById, obsFinales);
            String pdfUrl = assignmentUseCase.generarActa(id, deliveredById, obsFinales, imagenBytes);
            accepted.setPdfPath(pdfUrl);

            return ResponseEntity.ok(mapper.toResponse(accepted));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // <-- 2. RECHAZAR: Genera Acta -->
    @PostMapping(value = "/{id}/reject", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentResponse> rejectAssignment(
            @PathVariable UUID id,
            @RequestParam("deliveredById") UUID deliveredById,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        try {
            byte[] imagenBytes = (imagen != null && !imagen.isEmpty()) ? imagen.getBytes() : null;
            String obsFinales = (observaciones != null) ? observaciones : "Rechazado sin observaciones";
            
            AssetAssignment rejected = assignmentUseCase.rejectAssignment(id);
            String pdfUrl = assignmentUseCase.generarActa(id, deliveredById, obsFinales, imagenBytes);
            rejected.setPdfPath(pdfUrl);

            return ResponseEntity.ok(mapper.toResponse(rejected));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // <-- 3. TRANSFERIR: Convertido a form-data para generar Acta -->
    @PostMapping(value = "/{id}/transfer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentResponse> transferAsset(
            @PathVariable UUID id,
            @RequestParam("deliveredById") UUID deliveredById,
            @RequestParam("newAssigneeId") UUID newAssigneeId,
            @RequestParam(value = "transferReason", required = false) String transferReason,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        try {
            byte[] imagenBytes = (imagen != null && !imagen.isEmpty()) ? imagen.getBytes() : null;
            String obsFinales = (transferReason != null) ? transferReason : "Transferencia de equipo";

            AssetAssignment transferred = assignmentUseCase.transferAsset(id, deliveredById, newAssigneeId, obsFinales);
            String pdfUrl = assignmentUseCase.generarActa(id, deliveredById, obsFinales, imagenBytes);
            transferred.setPdfPath(pdfUrl);

            return ResponseEntity.ok(mapper.toResponse(transferred));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // <-- 4. DEVOLVER: Convertido a form-data para generar Acta -->
    @PostMapping(value = "/{id}/return", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentResponse> returnAsset(
            @PathVariable UUID id,
            @RequestParam("returnedById") UUID returnedById,
            @RequestParam(value = "returnReason", required = false) String returnReason,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        try {
            byte[] imagenBytes = (imagen != null && !imagen.isEmpty()) ? imagen.getBytes() : null;
            String obsFinales = (returnReason != null) ? returnReason : "Devolución a almacén";

            AssetAssignment returned = assignmentUseCase.returnAsset(id, returnedById, obsFinales);
            // Para el acta, la persona que devuelve hace la función de "entregar" (deliveredById)
            String pdfUrl = assignmentUseCase.generarActa(id, returnedById, obsFinales, imagenBytes);
            returned.setPdfPath(pdfUrl);

            return ResponseEntity.ok(mapper.toResponse(returned));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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