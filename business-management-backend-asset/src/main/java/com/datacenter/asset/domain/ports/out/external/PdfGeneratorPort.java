package com.datacenter.asset.domain.ports.out.external;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;

public interface PdfGeneratorPort {
    // Agregamos los nuevos parámetros de texto
    String generateAssignmentAct(AssetAssignment assignment, String personFirstName, String personLastName, String assetCode, String assetName);
}