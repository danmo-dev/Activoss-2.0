package com.datacenter.asset.application.usecases.asset;
 
import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetAssignmentUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
 
@Component
@RequiredArgsConstructor
public class ManageAssetAssignmentUseCaseImpl implements ManageAssetAssignmentUseCase {
 
    private final AssetAssignmentRepositoryPort assignmentRepository;
    private final AssetHistoryRepositoryPort historyRepository;
 
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
        assignment.setIsActive(false); // Desactivamos la asignación rechazada
        
        AssetAssignment updated = assignmentRepository.save(assignment);
        
        registerHistory(
                assignment.getAssetId(),
                "ASSIGNMENT_REJECTED",
                String.format("Asignación rechazada por %s", assignment.getPersonId()),
                assignment.getPersonId().toString()
        );
        
        return updated;
    }
 
    @Override
    @Transactional
    public AssetAssignment transferAsset(
            UUID assignmentId,
            UUID deliveredById,
            UUID newAssigneeId,
            String transferReason) {
        
        AssetAssignment currentAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BusinessException("Asignación no encontrada con id: " + assignmentId));
        
        // MODIFICACIÓN: Permitimos transferir si está ACCEPTED o si fue REJECTED (para reintentar)
        if (!currentAssignment.getState().equals(AssignmentState.ACCEPTED) && 
            !currentAssignment.getState().equals(AssignmentState.REJECTED)) {
            throw new BusinessException("Solo se pueden transferir asignaciones en estado ACCEPTED o REJECTED");
        }
        
        currentAssignment.setState(AssignmentState.TRANSFERRED);
        assignmentRepository.save(currentAssignment);
        
        String transferDescription = String.format("Transferencia de %s a %s. Motivo: %s", 
                deliveredById, newAssigneeId, 
                transferReason != null && !transferReason.isEmpty() ? transferReason : "Sin motivo");
        
        registerHistory(
                currentAssignment.getAssetId(),
                "ASSIGNMENT_TRANSFERRED",
                transferDescription,
                deliveredById.toString()
        );
        
        // Crear nueva asignación en pendiente para el nuevo usuario
        AssetAssignment newAssignment = AssetAssignment.builder()
                .assetId(currentAssignment.getAssetId())
                .personId(newAssigneeId)
                .state(AssignmentState.PENDING)
                .startDate(LocalDateTime.now())
                .isActive(true)
                .build();
        
        AssetAssignment saved = assignmentRepository.save(newAssignment);
        
        registerHistory(
                saved.getAssetId(),
                "ASSIGNMENT_CREATED",
                String.format("Activo transferido a %s desde %s", newAssigneeId, deliveredById),
                deliveredById.toString()
        );
        
        return saved;
    }
 
    @Override
    @Transactional
    public AssetAssignment returnAsset(UUID assignmentId, UUID returnedById, String returnReason) {
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
        
        registerHistory(
                assignment.getAssetId(),
                "ASSIGNMENT_RETURNED",
                description,
                returnedById.toString()
        );
        
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