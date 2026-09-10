package com.datacenter.asset.domain.ports.external.out;

import com.datacenter.asset.domain.assignment.AssetAssignment;

public interface PdfGeneratorPort {
    // Retorna la ruta donde se guardó el PDF generado
    String generateAssignmentAct(AssetAssignment assignment);
}