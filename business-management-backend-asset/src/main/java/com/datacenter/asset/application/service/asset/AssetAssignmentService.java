package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.asset.in.AssignAssetUseCase;
import com.datacenter.asset.domain.ports.asset.out.AssetAssignmentRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetAssignmentService implements AssignAssetUseCase {

    private final AssetAssignmentRepositoryPort assignmentRepositoryPort;

    @Override
    public AssetAssignment assignAsset(AssetAssignment assignment) {
        if (assignmentRepositoryPort.hasActiveAssignment(assignment.getAssetId())) {
            throw new RuntimeException("El activo ya tiene una asignación activa.");
        }
        
        assignment.setState(AssignmentState.PENDING);
        assignment.setStartDate(LocalDateTime.now());
        assignment.setIsActive(true);
        
        // TODO: Disparar evento para notificar a la persona (correo) - AC-EP02-HU28
        return assignmentRepositoryPort.save(assignment);
    }

    @Override
    public AssetAssignment acceptAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepositoryPort.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        
        // TODO: Lógica de generación de PDF - AC-EP02-HU31
        String mockPdfPath = "/docs/actas/" + assignmentId + ".pdf";
        assignment.acceptAssignment(mockPdfPath);
        
        return assignmentRepositoryPort.save(assignment);
    }

    @Override
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepositoryPort.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        
        assignment.rejectAssignment();
        return assignmentRepositoryPort.save(assignment);
    }
}
