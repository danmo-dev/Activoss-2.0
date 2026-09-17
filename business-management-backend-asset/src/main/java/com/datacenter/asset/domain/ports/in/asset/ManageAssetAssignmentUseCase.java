package com.datacenter.asset.domain.ports.in.asset;
 
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;

import java.util.List;
import java.util.UUID;
 
public interface ManageAssetAssignmentUseCase {
    
    AssetAssignment assignAsset(AssetAssignment assignment, UUID createdById);
    AssetAssignment acceptAssignment(UUID assignmentId, UUID deliveredById, String observaciones);
    AssetAssignment rejectAssignment(UUID assignmentId);
    List<AssetAssignment> getAssignmentsByState(AssignmentState state);
    List<AssetHistory> getAssetHistory(UUID assetId);
    AssetAssignment transferAsset(UUID assignmentId, UUID deliveredById, UUID newAssigneeId, String transferReason);
    AssetAssignment returnAsset(UUID assignmentId, UUID returnedById, String returnReason);
}