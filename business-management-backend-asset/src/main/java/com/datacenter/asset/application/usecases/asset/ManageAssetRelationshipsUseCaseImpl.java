package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.models.asset.AssetHistory;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetRelationshipsUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetRelationshipRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetHistoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementación combinada de ManageAssetRelationshipsUseCase
 * Incluye creación, consulta, actualización y eliminación de relaciones,
 * además de registro de historial.
 */
@Component
@Service
@RequiredArgsConstructor
public class ManageAssetRelationshipsUseCaseImpl implements ManageAssetRelationshipsUseCase {

    private final AssetRelationshipRepositoryPort relationshipRepository;
    private final AssetRepositoryPort assetRepository;
    private final AssetHistoryRepositoryPort historyRepository;

    @Override
    @Transactional
    public AssetRelationship createRelationship(AssetRelationship relationship) {
        if (assetRepository.findById(new AssetId(relationship.getParentAssetId())).isEmpty() ||
            assetRepository.findById(new AssetId(relationship.getChildAssetId())).isEmpty()) {
            throw new BusinessException("El activo padre o hijo no existe en el inventario.");
        }
        relationship.setRegistrationDate(LocalDateTime.now());
        return relationshipRepository.save(relationship);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRelationship> findChildrenByParentId(UUID parentAssetId) {
        return relationshipRepository.findByParentAssetId(parentAssetId);
    }

    @Override
    @Transactional
    public AssetRelationship update(UUID relationshipId, UUID newParentId, UUID newChildId, UUID newTypeId, String user) {
        AssetRelationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Relación no encontrada con ID: " + relationshipId));

        // Guardar historial de desvinculación si cambiaron los activos
        if (!relationship.getParentAssetId().equals(newParentId) || !relationship.getChildAssetId().equals(newChildId)) {
            registrarHistorial(relationship.getParentAssetId(), "RELATION_CHANGED",
                    "Se modificó la relación. Se desancló el activo hijo: " + relationship.getChildAssetId(), user);
            registrarHistorial(relationship.getChildAssetId(), "RELATION_CHANGED",
                    "Se modificó la relación. Se desancló del activo padre: " + relationship.getParentAssetId(), user);
        }

        // Actualizar valores
        relationship.setParentAssetId(newParentId);
        relationship.setChildAssetId(newChildId);
        relationship.setRelationshipTypeId(newTypeId);

        // Guardar relación actualizada
        AssetRelationship updated = relationshipRepository.save(relationship);

        // Guardar historial de nueva vinculación
        registrarHistorial(newParentId, "RELATION_UPDATED",
                "Se vinculó un nuevo activo hijo: " + newChildId, user);
        registrarHistorial(newChildId, "RELATION_UPDATED",
                "Se vinculó a un nuevo activo padre: " + newParentId, user);

        return updated;
    }

    @Override
    @Transactional
    public void delete(UUID relationshipId, String user) {
        AssetRelationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Relación no encontrada con ID: " + relationshipId));

        UUID parentId = relationship.getParentAssetId();
        UUID childId = relationship.getChildAssetId();

        relationshipRepository.deleteById(relationshipId);

        registrarHistorial(parentId, "RELATION_DELETED",
                "Se desancló el activo hijo: " + childId, user);
        registrarHistorial(childId, "RELATION_DELETED",
                "Se desancló del activo padre: " + parentId, user);
    }

    private void registrarHistorial(UUID assetId, String action, String details, String user) {
        AssetHistory history = AssetHistory.builder()
                .assetId(assetId)
                .eventType(action)
                .description(details)
                .eventDate(LocalDateTime.now())
                .executedBy(user != null ? user : "SYSTEM")
                .build();

        historyRepository.save(history);
    }
}
