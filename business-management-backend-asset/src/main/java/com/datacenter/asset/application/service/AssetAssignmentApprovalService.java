package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.in.ApproveAssignmentUseCase;
import com.datacenter.asset.domain.ports.out.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.EmailNotificationPort;
import com.datacenter.asset.domain.ports.out.PdfGeneratorPort;
import com.datacenter.asset.domain.ports.out.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.PersonRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AssetAssignmentApprovalService implements ApproveAssignmentUseCase {

    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final AssetRepositoryPort assetRepository;
    private final PersonRepositoryPort personRepository;
    private final PdfGeneratorPort pdfGenerator;
    private final EmailNotificationPort emailNotification;

    public AssetAssignmentApprovalService(AssetAssignmentRepositoryPort assignmentRepository,
                                          AssetRepositoryPort assetRepository,
                                          PersonRepositoryPort personRepository,
                                          PdfGeneratorPort pdfGenerator,
                                          EmailNotificationPort emailNotification) {
        this.assignmentRepository = assignmentRepository;
        this.assetRepository = assetRepository;
        this.personRepository = personRepository;
        this.pdfGenerator = pdfGenerator;
        this.emailNotification = emailNotification;
    }

    @Override
    @Transactional
    public AssetAssignment acceptAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // 1. Generar Acta PDF según RF-08
        String pdfPath = pdfGenerator.generateAssignmentAct(assignment);
        
        // 2. Cambiar estado de la asignación
        assignment.accept(pdfPath);
        
        // Nota: Aquí iría la lógica para actualizar el Asset a estado "ASSIGNED" usando assetRepository

        AssetAssignment savedAssignment = assignmentRepository.save(assignment);

        // 3. Notificar al colaborador
        personRepository.findById(assignment.getPersonId()).ifPresent(person -> 
            emailNotification.sendAssignmentAcceptedNotification(person.getEmail(), savedAssignment, pdfPath)
        );

        return savedAssignment;
    }

    @Override
    @Transactional
    public AssetAssignment rejectAssignment(UUID assignmentId, String reason) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        assignment.reject(reason);
        return assignmentRepository.save(assignment);
    }
}