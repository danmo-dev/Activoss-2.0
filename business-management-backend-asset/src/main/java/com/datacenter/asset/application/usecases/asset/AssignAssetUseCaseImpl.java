package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.AssignAssetUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.external.EmailNotificationPort; // <-- agregado
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignAssetUseCaseImpl implements AssignAssetUseCase {

    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final PdfGeneratorPort pdfGenerator;
    private final EmailNotificationPort emailNotification;   // <-- agregado
    private final AssetRepositoryPort assetRepository; 
    private final PersonRepositoryPort personRepository;

    @Override
    @Transactional
    public AssetAssignment assignAsset(AssetAssignment assignment) {
        if (assignmentRepository.hasActiveAssignment(assignment.getAssetId())) {
            throw new BusinessException("El activo ya tiene una asignación activa.");
        }
        assignment.setState(AssignmentState.PENDING);
        assignment.setStartDate(LocalDateTime.now());
        assignment.setIsActive(true);

        AssetAssignment saved = assignmentRepository.save(assignment);

        // Notificación de asignación pendiente
        var person = personRepository.findById(saved.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        emailNotification.sendAssignmentPendingNotification(person.getEmail(), saved);

        return saved;
    }

    @Override
    @Transactional
    public AssetAssignment acceptAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        var person = personRepository.findById(assignment.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        var asset = assetRepository.findById(assignment.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado"));

        String pdfUrl = pdfGenerator.generateAssignmentAct(
                assignment, person.getFirstName(), person.getLastName(), 
                asset.getCode().value(), asset.getName()
        );

        assignment.accept(pdfUrl);
        AssetAssignment saved = assignmentRepository.save(assignment);

        // Notificación de aceptación con acta
        emailNotification.sendAssignmentAcceptedNotification(person.getEmail(), saved, pdfUrl);

        return saved;
    }

    @Override
    @Transactional
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        assignment.rejectAssignment();
        AssetAssignment saved = assignmentRepository.save(assignment);

        // Notificación de rechazo
        var person = personRepository.findById(saved.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        emailNotification.sendAssignmentRejectedNotification(person.getEmail(), saved, "Asignación rechazada por el usuario");

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetAssignment findById(UUID assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));
    }
}
