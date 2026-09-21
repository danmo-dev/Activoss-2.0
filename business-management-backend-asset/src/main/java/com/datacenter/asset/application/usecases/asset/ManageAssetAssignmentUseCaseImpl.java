package com.datacenter.asset.application.usecases.asset;
 
import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetAssignmentUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetStatusRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import com.datacenter.asset.domain.ports.out.external.EmailNotificationPort;
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldDefinitionRepositoryPort;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import com.datacenter.asset.domain.ports.out.person.PersonRepositoryPort;
 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
 
@Component
@RequiredArgsConstructor
public class ManageAssetAssignmentUseCaseImpl implements ManageAssetAssignmentUseCase {
 
    // Repositorios de ManageAssetAssignmentUseCase
    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final AssetHistoryRepositoryPort historyRepository;
    
    // Repositorios combinados de AssignAssetUseCase (Para generar el PDF)
    private final PdfGeneratorPort pdfGenerator;
    private final EmailNotificationPort emailNotification;
    private final AssetRepositoryPort assetRepository; 
    private final PersonRepositoryPort personRepository;
    private final LocationRepositoryPort locationRepository; 
    private final CompanyRepositoryPort companyRepository;   
    private final AssetStatusRepositoryPort assetStatusRepository;
    private final AssetValueRepositoryPort assetValueRepository;
    private final FieldDefinitionRepositoryPort fieldDefinitionRepository;
 
    @Override
    @Transactional
    public AssetAssignment assignAsset(AssetAssignment assignment, UUID createdById) {
        assignment.setState(AssignmentState.PENDING);
        assignment.setStartDate(LocalDateTime.now());
        assignment.setIsActive(true);
        
        AssetAssignment saved = assignmentRepository.save(assignment);
        
        String actorStr = (createdById != null) ? createdById.toString() : "SYSTEM";
        registerHistory(
                saved.getAssetId(),
                "ASSIGNMENT_CREATED",
                String.format("Activo asignado a %s por %s", assignment.getPersonId(), actorStr),
                actorStr
        );
        
        return saved;
    }
 
    @Override
    @Transactional
    public AssetAssignment acceptAssignment(UUID assignmentId, UUID deliveredById, String observaciones) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));
        
        if (!assignment.getState().equals(AssignmentState.PENDING)) {
            throw new BusinessException("Solo se pueden aceptar asignaciones en estado PENDING");
        }
        
        assignment.setState(AssignmentState.ACCEPTED);
        assignment.setAcceptanceDate(LocalDateTime.now());
        
        AssetAssignment updated = assignmentRepository.save(assignment);
        
        String obsStr = (observaciones != null && !observaciones.isEmpty()) ? ". Observaciones: " + observaciones : "";
        String description = String.format("Asignación aceptada por %s. Entregado por: %s%s", 
                assignment.getPersonId(), deliveredById, obsStr);
        
        registerHistory(
                assignment.getAssetId(),
                "ASSIGNMENT_ACCEPTED",
                description,
                assignment.getPersonId().toString()
        );
        
        return updated;
    }
 
    @Override
    @Transactional
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));
        
        if (!assignment.getState().equals(AssignmentState.PENDING)) {
            throw new BusinessException("Solo se pueden rechazar asignaciones en estado PENDING");
        }
        
        assignment.setState(AssignmentState.REJECTED);
        assignment.setIsActive(false); 
        
        AssetAssignment updated = assignmentRepository.save(assignment);
        
        registerHistory(
                assignment.getAssetId(),
                "ASSIGNMENT_REJECTED",
                String.format("Asignación rechazada por %s", assignment.getPersonId()),
                assignment.getPersonId().toString()
        );
        
        return updated;
    }

    // <-- MÉTODO COMBINADO PARA GENERAR ACTA CON PDF (CORREGIDO EL ERROR DE LA IMAGEN) -->
    @Override
    @Transactional
    public String generarActa(UUID assignmentId, UUID deliveredById, String observaciones, byte[] imagenObservacion) {
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

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

        // Generar PDF pasando la imagen (Error corregido: Ya no se envía el String al final)
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
                fechaHoraAct,
                imagenObservacion // <-- Termina con byte[] exacto como lo pide la interfaz
        );

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
    public AssetAssignment transferAsset(UUID assignmentId, UUID deliveredById, UUID newAssigneeId, String transferReason) {
        // ... (Tu código de transferencia intacto) ...
        AssetAssignment currentAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));
        
        if (!currentAssignment.getState().equals(AssignmentState.ACCEPTED) && 
            !currentAssignment.getState().equals(AssignmentState.REJECTED)) {
            throw new BusinessException("Solo se pueden transferir asignaciones en estado ACCEPTED o REJECTED");
        }
        
        currentAssignment.setState(AssignmentState.TRANSFERRED);
        assignmentRepository.save(currentAssignment);
        
        String transferDescription = String.format("Transferencia de %s a %s. Motivo: %s", 
                deliveredById, newAssigneeId, 
                transferReason != null && !transferReason.isEmpty() ? transferReason : "Sin motivo");
        
        registerHistory(currentAssignment.getAssetId(), "ASSIGNMENT_TRANSFERRED", transferDescription, deliveredById.toString());
        
        AssetAssignment newAssignment = AssetAssignment.builder()
                .assetId(currentAssignment.getAssetId())
                .personId(newAssigneeId)
                .state(AssignmentState.PENDING)
                .startDate(LocalDateTime.now())
                .isActive(true)
                .build();
        
        AssetAssignment saved = assignmentRepository.save(newAssignment);
        
        registerHistory(saved.getAssetId(), "ASSIGNMENT_CREATED",
                String.format("Activo transferido a %s desde %s", newAssigneeId, deliveredById),
                deliveredById.toString());
        
        return saved;
    }
 
    @Override
    @Transactional
    public AssetAssignment returnAsset(UUID assignmentId, UUID returnedById, String returnReason) {
        // ... (Tu código de retorno intacto) ...
        AssetAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));
        
        if (!assignment.getState().equals(AssignmentState.ACCEPTED)) {
            throw new BusinessException("Solo se pueden devolver activos en estado ACCEPTED");
        }
        
        assignment.setState(AssignmentState.RETURNED);
        assignment.setIsActive(false); 
        
        AssetAssignment updated = assignmentRepository.save(assignment);
        
        String description = String.format("Activo devuelto al almacén por %s. Motivo: %s", 
                returnedById, 
                returnReason != null && !returnReason.isEmpty() ? returnReason : "Sin motivo");
        
        registerHistory(assignment.getAssetId(), "ASSIGNMENT_RETURNED", description, returnedById.toString());
        
        return updated;
    }
    
    private void registerHistory(UUID assetId, String eventType, String description, String actor) {
        AssetHistory history = AssetHistory.builder()
                .assetId(assetId)
                .eventDate(LocalDateTime.now())
                .eventType(eventType)
                .executedBy(actor)
                .description(description)
                .build();
        
        historyRepository.save(history);
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<AssetAssignment> getAssignmentsByState(AssignmentState state) {
        return assignmentRepository.findByState(state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetHistory> getAssetHistory(UUID assetId) {
        return historyRepository.findByAssetId(assetId);
    }
}