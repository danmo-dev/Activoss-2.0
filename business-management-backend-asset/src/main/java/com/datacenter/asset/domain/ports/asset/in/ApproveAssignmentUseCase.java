package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import java.util.UUID;

public interface ApproveAssignmentUseCase {
    AssetAssignment acceptAssignment(UUID assignmentId);
    AssetAssignment rejectAssignment(UUID assignmentId, String reason);
}