package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.AssignAssetUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import com.datacenter.asset.domain.ports.out.external.EmailNotificationPort;
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldDefinitionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignAssetUseCaseImpl implements AssignAssetUseCase {

    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final PdfGeneratorPort pdfGenerator;
    private final EmailNotificationPort emailNotification;
    private final AssetRepositoryPort assetRepository; 
    private final PersonRepositoryPort personRepository;
    private final LocationRepositoryPort locationRepository; 
    private final CompanyRepositoryPort companyRepository;   

    // Puertos adicionales para EAV y estado
    private final AssetStatusRepositoryPort assetStatusRepository;
    private final AssetValueRepositoryPort assetValueRepository;
    private final FieldDefinitionRepositoryPort fieldDefinitionRepository;

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
        var person = personRepository.findById(saved.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        emailNotification.sendAssignmentPendingNotification(person.getEmail(), saved);
        return saved;
    }

    @Override
    @Transactional
    public AssetAssignment acceptAssignment(UUID assignmentId, UUID deliveredById, String observaciones) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));
        
        // Simplemente aceptamos la asignación y guardamos observaciones (El PDF va después)
        assignment.accept("", observaciones);
        return assignmentRepository.save(assignment);
    }

    // NUEVO MÉTODO PARA ENDPOINT /acta (Genera PDF, EAV y envía el correo)
    @Override
    @Transactional
    public String generarActa(UUID assignmentId, UUID deliveredById, String observaciones) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        // LÓGICA DE ESTADO FLEXIBLE:
        // Si sigue PENDIENTE, la aceptamos automáticamente. Si ya está ACEPTADA, continuamos sin lanzar error.
        if (assignment.getState() == AssignmentState.PENDING) {
            assignment.accept("", observaciones);
        }

        var person = personRepository.findById(assignment.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona que recibe no encontrada"));
        var deliverer = personRepository.findById(deliveredById)
                .orElseThrow(() -> new ResourceNotFoundException("Persona que entrega no encontrada"));
        var asset = assetRepository.findById(assignment.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado"));
        var location = locationRepository.findById(asset.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada"));
        var company = companyRepository.findById(asset.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        var status = assetStatusRepository.findById(asset.getAssetStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        // Lógica EAV: Extraer atributos dinámicos
        List<AssetValue> assetValues = assetValueRepository.findByAssetId(asset.getId().value());
        String marca = "", modelo = "", procesador = "", placa = "", atributo = "", serial = "";

        for (AssetValue val : assetValues) {
            var defOpt = fieldDefinitionRepository.findById(val.getFieldDefinitionId());
            if (defOpt.isPresent()) {
                String labelName = defOpt.get().getName().toLowerCase();
                if (labelName.contains("marca")) marca = val.getValue();
                else if (labelName.contains("modelo")) modelo = val.getValue();
                else if (labelName.contains("procesador")) procesador = val.getValue();
                else if (labelName.contains("placa") || labelName.contains("numer placa")) placa = val.getValue();
                else if (labelName.contains("atributo")) atributo = val.getValue();
                else if (labelName.contains("serial")) serial = val.getValue();
            }
        }

        String fechaHoraAct = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Generar PDF
        String pdfUrl = pdfGenerator.generateAssignmentAct(
                assignment, 
                person.getFirstName(), 
                person.getLastName(), 
                person.getDocumentNumber(), 
                person.getEmail(),
                deliverer.getFirstName(),
                deliverer.getLastName(),
                deliverer.getDocumentNumber(),
                deliverer.getEmail(),
                asset.getCode().value(), 
                asset.getName(),
                location.getCode(),         
                company.getTaxId(),         
                company.getName(),          
                observaciones,
                serial,           
                marca,            
                modelo,           
                procesador,       
                status.getName(), 
                placa,            
                atributo,
                fechaHoraAct        
        );

        // Actualizamos la ruta del PDF en la asignación
        assignment.setPdfPath(pdfUrl);
        if (observaciones != null && !observaciones.isEmpty()) {
            assignment.setNotes(observaciones);
        }

        AssetAssignment saved = assignmentRepository.save(assignment);
        emailNotification.sendAssignmentAcceptedNotification(person.getEmail(), saved, pdfUrl);

        return "http://localhost:8080" + pdfUrl;
    }

    @Override
    @Transactional
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        assignment.rejectAssignment();
        AssetAssignment saved = assignmentRepository.save(assignment);

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