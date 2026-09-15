package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetLifecycleService {

    private final AssetRepositoryPort assetRepository;
    private final AssetHistoryRepositoryPort historyRepository;
    private final AssetAssignmentRepositoryPort assignmentRepository;

    @Transactional
    public void deactivate(UUID assetId, String reason, String executedBy) {
        changeAssetState(assetId, false, "INACTIVACION", reason, executedBy);
    }

    @Transactional
    public void reactivate(UUID assetId, String reason, String executedBy) {
        changeAssetState(assetId, true, "REACTIVACION", reason, executedBy);
    }

    @Transactional
    public void decommission(UUID assetId, String reason, String executedBy) {
        if (assignmentRepository.hasActiveAssignment(assetId)) {
            throw new IllegalStateException("No se puede dar de baja un activo que tiene una asignación activa (RF-09).");
        }
        changeAssetState(assetId, false, "BAJA", reason, executedBy);
    }

    public List<AssetHistory> getHistory(UUID assetId) {
        return historyRepository.findByAssetId(assetId);
    }

    private void changeAssetState(UUID assetId, boolean isActive, String eventType, String reason, String executedBy) {
        Asset asset = assetRepository.findById(new AssetId(assetId))
                .orElseThrow(() -> new IllegalArgumentException("Activo no encontrado"));

        Asset updatedAsset = asset.withActiveState(isActive);
        assetRepository.save(updatedAsset);

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
