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
    
    // Campos requeridos por las épicas (AC-EP02)
    private AssignmentState state;
    private LocalDateTime acceptanceDate;
    private String pdfPath;

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
}