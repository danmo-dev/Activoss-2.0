package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import java.util.UUID;

public interface AssignAssetUseCase {
    AssetAssignment assignAsset(AssetAssignment assignment);
    AssetAssignment acceptAssignment(UUID assignmentId);
    AssetAssignment rejectAssignment(UUID assignmentId);
    AssetAssignment findById(UUID assignmentId);
}