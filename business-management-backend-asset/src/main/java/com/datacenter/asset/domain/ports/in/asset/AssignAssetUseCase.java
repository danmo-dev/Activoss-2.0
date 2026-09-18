package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;

import java.util.UUID;

public interface AssignAssetUseCase {
    AssetAssignment assignAsset(AssetAssignment assignment);
    // Agregamos deliveredById a la interfaz
    AssetAssignment acceptAssignment(UUID assignmentId, UUID deliveredById, String observaciones);
    AssetAssignment rejectAssignment(UUID assignmentId);
    AssetAssignment findById(UUID assignmentId);
    String generarActa(UUID assignmentId, UUID deliveredById, String observaciones);
}