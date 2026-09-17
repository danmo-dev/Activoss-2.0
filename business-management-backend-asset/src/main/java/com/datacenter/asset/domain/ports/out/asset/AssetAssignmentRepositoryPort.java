package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetAssignmentRepositoryPort {
    AssetAssignment save(AssetAssignment assignment);
    Optional<AssetAssignment> findById(UUID id);
    boolean hasActiveAssignment(UUID assetId);
    List<AssetAssignment> findByState(AssignmentState state);
}