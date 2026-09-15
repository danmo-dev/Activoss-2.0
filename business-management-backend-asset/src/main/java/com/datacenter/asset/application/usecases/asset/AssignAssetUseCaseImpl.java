package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.in.asset.AssignAssetUseCase;
import com.datacenter.asset.application.service.asset.AssetAssignmentService;
import com.datacenter.asset.application.service.external.pdfgenerator.AssetAssignmentApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignAssetUseCaseImpl implements AssignAssetUseCase {

    private final AssetAssignmentService assignmentService;
    private final AssetAssignmentApprovalService approvalService;

    @Override
    public AssetAssignment assignAsset(AssetAssignment assignment) {
        // Delega al servicio general
        return assignmentService.assignAsset(assignment);
    }

    @Override
    public AssetAssignment acceptAssignment(UUID assignmentId) {
        // Delega al servicio especializado en aprobar y generar el PDF real
        return approvalService.approveAssignment(assignmentId);
    }

    @Override
    public AssetAssignment rejectAssignment(UUID assignmentId) {
        // Delega al servicio general
        return assignmentService.rejectAssetAssignment(assignmentId);
    }
}