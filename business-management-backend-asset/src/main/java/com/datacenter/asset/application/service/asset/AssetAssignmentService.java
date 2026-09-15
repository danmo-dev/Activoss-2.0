package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetAssignmentService {

    private final AssetAssignmentRepositoryPort assignmentRepositoryPort;

    public AssetAssignment assignAsset(AssetAssignment assignment) {
        if (assignmentRepositoryPort.hasActiveAssignment(assignment.getAssetId())) {
            throw new RuntimeException("El activo ya tiene una asignación activa.");
        }

        assignment.setState(AssignmentState.PENDING);
        assignment.setStartDate(LocalDateTime.now());
        assignment.setIsActive(true);

        return assignmentRepositoryPort.save(assignment);
    }

    public AssetAssignment rejectAssetAssignment(UUID assignmentId) {
        AssetAssignment assignment = assignmentRepositoryPort.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        assignment.rejectAssignment(); // Asegúrate de que este método en tu modelo cambie el estado y asigne la fecha de fin
        return assignmentRepositoryPort.save(assignment);
    }
}