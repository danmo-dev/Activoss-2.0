package com.datacenter.asset.domain.models.assignment;

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

    /**
     * ID del lote. Varias asignaciones creadas en el mismo POST
     * comparten este batchId. NO se persiste como columna propia;
     * viaja dentro del campo `notes` como prefijo "[BATCH:uuid]".
     */
    private UUID batchId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String notes;
    private Boolean isActive;

    private AssignmentState state;
    private LocalDateTime acceptanceDate;
    private String pdfPath;
    private String rejectionReason;
    private String observaciones;

    public void accept(String generatedPdfPath, String observaciones) {
        if (this.state != AssignmentState.PENDING) {
            throw new IllegalStateException("Solo se pueden aceptar asignaciones en estado PENDIENTE.");
        }
        this.state = AssignmentState.ACCEPTED;
        this.acceptanceDate = LocalDateTime.now();
        this.pdfPath = generatedPdfPath;
        this.observaciones = observaciones;
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