package com.datacenter.asset.infrastructure.adapters.out.external;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.out.PdfGeneratorPort;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class PdfGeneratorAdapter implements PdfGeneratorPort {
    @Override
    public String generateAssignmentAct(AssetAssignment assignment) {
        String mockPath = "/storage/actas/acta_entrega_" + UUID.randomUUID().toString() + ".pdf";
        System.out.println("[PDF Generator] Simulando creación de acta en: " + mockPath);
        return mockPath;
    }
}