package com.datacenter.asset.domain.ports.out.external;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;

public interface PdfGeneratorPort {

    String generateAssignmentAct(
            AssetAssignment assignment,
            String personFirstName,
            String personLastName,
            String personDocumentNumber,
            String personEmail,
            String delivererFirstName,
            String delivererLastName,
            String delivererDocumentNumber,
            String delivererEmail,
            String assetCode,
            String assetName,
            String locationCode,
            String companyTaxId,
            String companyName,
            String observaciones,
            String assetSerial,
            String assetMarca,
            String assetModelo,
            String assetProcesador,
            String assetEstado,
            String assetPlaca,
            String assetAtributo,
            String fechaHora,
            byte[] imagenObservacion
    );
}