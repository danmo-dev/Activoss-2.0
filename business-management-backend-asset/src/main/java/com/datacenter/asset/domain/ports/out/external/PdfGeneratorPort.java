package com.datacenter.asset.domain.ports.out.external;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentActItem;

import java.util.List;

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
            List<AssignmentActItem> assets,
            String companyTaxId,
            String companyName,
            String observaciones,
            String fechaHora,
            byte[] imagenObservacion
    );
}