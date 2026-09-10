package com.datacenter.asset.domain.ports.asset.out;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import java.util.Optional;
import java.util.UUID;

public interface AssetAssignmentRepositoryPort {
    AssetAssignment save(AssetAssignment assignment);
    Optional<AssetAssignment> findById(UUID id);
    boolean hasActiveAssignment(UUID assetId);
}