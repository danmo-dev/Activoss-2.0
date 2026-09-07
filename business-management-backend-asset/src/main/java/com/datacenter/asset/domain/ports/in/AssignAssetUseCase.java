package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import java.util.UUID;

public interface AssignAssetUseCase {
    AssetAssignment assignAsset(AssetAssignment assignment);
    AssetAssignment acceptAssignment(UUID assignmentId);
    AssetAssignment rejectAssignment(UUID assignmentId);
}