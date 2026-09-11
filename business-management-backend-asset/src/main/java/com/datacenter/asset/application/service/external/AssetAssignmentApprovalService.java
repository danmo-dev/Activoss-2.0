package com.datacenter.asset.application.service.external;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.asset.out.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.external.out.PdfGeneratorPort;
// Importa tus repositorios o puertos de Activo y Persona
import com.datacenter.asset.domain.ports.asset.out.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.person.out.PersonRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssetAssignmentApprovalService {

    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final PdfGeneratorPort pdfGenerator;
    // Agregamos los repositorios necesarios
    private final AssetRepositoryPort assetRepository; 
    private final PersonRepositoryPort personRepository;

    public AssetAssignmentApprovalService(AssetAssignmentRepositoryPort assignmentRepository,
                                          PdfGeneratorPort pdfGenerator,
                                          AssetRepositoryPort assetRepository,
                                          PersonRepositoryPort personRepository) {
        this.assignmentRepository = assignmentRepository;
        this.pdfGenerator = pdfGenerator;
        this.assetRepository = assetRepository;
        this.personRepository = personRepository;
    }

    public AssetAssignment approveAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // 1. Buscar los datos reales usando los IDs
        // (Ajusta los métodos getNombre() / getCodigo() según tus entidades)
        var person = personRepository.findById(assignment.getPersonId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        
        var asset = assetRepository.findById(assignment.getAssetId())
                .orElseThrow(() -> new RuntimeException("Activo no encontrado"));

        // 2. Pasar los datos extraídos al generador
        String pdfUrl = pdfGenerator.generateAssignmentAct(
                assignment,
                person.getFirstName(),               // String
                person.getLastName(),                // String
                asset.getCode().value(),          // convertir a String
                asset.getName()                      // String
        );

        assignment.accept(pdfUrl);
        return assignmentRepository.save(assignment);
    }

    public AssetAssignment findById(UUID id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }
}