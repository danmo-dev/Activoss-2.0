package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetHistory;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.ports.in.ManageAssetLifecycleUseCase;
import com.datacenter.asset.domain.ports.out.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.AssetHistoryRepositoryPort;
import com.datacenter.asset.domain.ports.out.IAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetLifecycleService implements ManageAssetLifecycleUseCase {

    private final IAssetRepository assetRepository;
    private final AssetHistoryRepositoryPort historyRepository;
    private final AssetAssignmentRepositoryPort assignmentRepository;

    @Override
    @Transactional
    public void deactivateAsset(UUID assetId, String reason, String executedBy) {
        changeAssetState(assetId, false, "INACTIVACION", reason, executedBy);
    }

    @Override
    @Transactional
    public void reactivateAsset(UUID assetId, String reason, String executedBy) {
        changeAssetState(assetId, true, "REACTIVACION", reason, executedBy);
    }

    @Override
    @Transactional
    public void decommissionAsset(UUID assetId, String reason, String executedBy) {
        if (assignmentRepository.hasActiveAssignment(assetId)) {
            throw new IllegalStateException("No se puede dar de baja un activo que tiene una asignación activa (RF-09).");
        }
        changeAssetState(assetId, false, "BAJA", reason, executedBy);
    }

    @Override
    public List<AssetHistory> getAssetHistory(UUID assetId) {
        return historyRepository.findByAssetId(assetId);
    }

    private void changeAssetState(UUID assetId, boolean isActive, String eventType, String reason, String executedBy) {
        Asset asset = assetRepository.findById(new AssetId(assetId))
                .orElseThrow(() -> new IllegalArgumentException("Activo no encontrado"));

        // Generar nueva instancia respetando inmutabilidad y persistir
        Asset updatedAsset = asset.withActiveState(isActive);
        assetRepository.save(updatedAsset);

        // Registrar trazabilidad obligatoria (RF-07)
        var history = AssetHistory.builder()
                .assetId(assetId)
                .eventDate(LocalDateTime.now())
                .eventType(eventType)
                .executedBy(executedBy)
                .description(reason)
                .build();

        historyRepository.save(history);
    }

}