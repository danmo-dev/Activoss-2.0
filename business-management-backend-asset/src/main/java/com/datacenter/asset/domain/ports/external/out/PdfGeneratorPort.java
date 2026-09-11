package com.datacenter.asset.domain.ports.external.out;

import com.datacenter.asset.domain.assignment.AssetAssignment;

public interface PdfGeneratorPort {
    // Agregamos los nuevos parámetros de texto
    String generateAssignmentAct(AssetAssignment assignment, String personFirstName, String personLastName, String assetCode, String assetName);
}