package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetLifecycleUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManageAssetLifecycleUseCaseImpl implements ManageAssetLifecycleUseCase {

    private final AssetRepositoryPort assetRepository;
    private final AssetHistoryRepositoryPort historyRepository;
    private final AssetAssignmentRepositoryPort assignmentRepository;

    @Override
    @Transactional
    public void deactivateAsset(UUID assetId, String reason, String executedBy) {
        log.info("Inactivando activo con ID: {}", assetId);
        changeAssetState(assetId, false, "INACTIVACION", reason, executedBy);
    }

    @Override
    @Transactional
    public void reactivateAsset(UUID assetId, String reason, String executedBy) {
        log.info("Reactivando activo con ID: {}", assetId);
        changeAssetState(assetId, true, "REACTIVACION", reason, executedBy);
    }

    @Override
    @Transactional
    public void decommissionAsset(UUID assetId, String reason, String executedBy) {
        log.info("Dando de baja activo con ID: {}", assetId);
        if (assignmentRepository.hasActiveAssignment(assetId)) {
            throw new BusinessException("No se puede dar de baja un activo con asignación activa (RF-09).");
        }
        changeAssetState(assetId, false, "BAJA", reason, executedBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetHistory> getAssetHistory(UUID assetId) {
        log.info("Obteniendo historial de activo con ID: {}", assetId);
        return historyRepository.findByAssetId(assetId);
    }

    private void changeAssetState(UUID assetId, boolean isActive, String eventType, String reason, String executedBy) {
        log.info("Cambiando estado de activo con ID: {}", assetId);
        Asset asset = assetRepository.findById(new AssetId(assetId))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado"));

        assetRepository.save(asset.withActiveState(isActive));

        historyRepository.save(AssetHistory.builder()
                .assetId(assetId).eventDate(LocalDateTime.now())
                .eventType(eventType).executedBy(executedBy).description(reason).build());
    }
}