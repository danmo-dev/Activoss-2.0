package com.datacenter.asset.domain.assignment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AssetAssignment {
    private UUID id;
    private UUID assetId;
    private UUID personId;
    private UUID relationshipTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;
    private Boolean isActive;

    // Campos requeridos por las épicas (AC-EP02 / BD-EP01-HU11)
    private AssignmentState state;
    private LocalDateTime acceptanceDate;
    private String pdfPath;
    private String rejectionReason;

    // Constructor vacío (aportado por la segunda versión)
    public void AssetAssignment() {}

    // Métodos originales
    public void acceptAssignment(String generatedPdfPath) {
        this.state = AssignmentState.ACCEPTED;
        this.acceptanceDate = LocalDateTime.now();
        this.pdfPath = generatedPdfPath;
    }

    public void rejectAssignment() {
        this.state = AssignmentState.REJECTED;
        this.isActive = false;
        this.endDate = LocalDateTime.now();
    }

    // Métodos con validación de estado
    public void accept(String generatedPdfPath) {
        if (this.state != AssignmentState.PENDING) {
            throw new IllegalStateException("Solo se pueden aceptar asignaciones en estado PENDIENTE.");
        }
        this.state = AssignmentState.ACCEPTED;
        this.acceptanceDate = LocalDateTime.now();
        this.pdfPath = generatedPdfPath;
        this.isActive = true;
    }

    public void reject(String reason) {
        if (this.state != AssignmentState.PENDING) {
            throw new IllegalStateException("Solo se pueden rechazar asignaciones en estado PENDIENTE.");
        }
        this.state = AssignmentState.REJECTED;
        this.rejectionReason = reason;
        this.isActive = false;
        this.endDate = LocalDateTime.now();
    }
}
