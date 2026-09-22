package com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetAssignmentEntity;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AssetAssignmentPersistenceMapper {

    private static final String BATCH_PREFIX = "[BATCH:";
    private static final Pattern BATCH_PATTERN = Pattern.compile("^\\[BATCH:([0-9a-fA-F\\-]{36})\\]\\s?(.*)$", Pattern.DOTALL);

    public AssetAssignmentEntity toEntity(AssetAssignment domain) {
        if (domain == null) return null;

        AssetAssignmentEntity entity = new AssetAssignmentEntity();
        entity.setId(domain.getId());
        entity.setAssetId(domain.getAssetId());
        entity.setPersonId(domain.getPersonId());
        entity.setRelationshipTypeId(domain.getRelationshipTypeId());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setIsActive(domain.getIsActive());
        entity.setState(domain.getState() != null ? domain.getState().name() : null);
        entity.setAcceptanceDate(domain.getAcceptanceDate());
        entity.setPdfPath(domain.getPdfPath());
        entity.setRejectionReason(domain.getRejectionReason());

        // Serializar batchId dentro de notes como "[BATCH:<uuid>] <notes originales>"
        String userNotes = domain.getNotes() != null ? domain.getNotes() : "";
        if (domain.getBatchId() != null) {
            entity.setNotes(BATCH_PREFIX + domain.getBatchId() + "] " + userNotes);
        } else {
            entity.setNotes(userNotes);
        }

        return entity;
    }

    public AssetAssignment toDomain(AssetAssignmentEntity entity) {
        if (entity == null) return null;

        UUID batchId = null;
        String userNotes = entity.getNotes();

        // Extraer batchId del prefijo de notes (si existe)
        if (userNotes != null) {
            Matcher m = BATCH_PATTERN.matcher(userNotes);
            if (m.matches()) {
                try {
                    batchId = UUID.fromString(m.group(1));
                } catch (IllegalArgumentException ignored) {}
                userNotes = m.group(2);
            }
        }

        return AssetAssignment.builder()
                .id(entity.getId())
                .assetId(entity.getAssetId())
                .personId(entity.getPersonId())
                .relationshipTypeId(entity.getRelationshipTypeId())
                .batchId(batchId)
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .notes(userNotes)
                .isActive(entity.getIsActive())
                .state(entity.getState() != null ? AssignmentState.valueOf(entity.getState()) : null)
                .acceptanceDate(entity.getAcceptanceDate())
                .pdfPath(entity.getPdfPath())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }
}