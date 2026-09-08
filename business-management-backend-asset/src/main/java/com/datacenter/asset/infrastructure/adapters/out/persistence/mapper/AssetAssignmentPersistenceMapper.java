package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.assignment.AssignmentState;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetAssignmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetAssignmentPersistenceMapper {

    public AssetAssignmentEntity toEntity(AssetAssignment domain) {
        if (domain == null) return null;
        AssetAssignmentEntity entity = new AssetAssignmentEntity();
        entity.setId(domain.getId());
        entity.setAssetId(domain.getAssetId());
        entity.setPersonId(domain.getPersonId());
        entity.setRelationshipTypeId(domain.getRelationshipTypeId());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setNotes(domain.getNotes());
        entity.setIsActive(domain.getIsActive());
        entity.setState(domain.getState() != null ? domain.getState().name() : null);
        entity.setAcceptanceDate(domain.getAcceptanceDate());
        entity.setPdfPath(domain.getPdfPath());
        entity.setRejectionReason(domain.getRejectionReason());
        return entity;
    }

    public AssetAssignment toDomain(AssetAssignmentEntity entity) {
        if (entity == null) return null;
        return AssetAssignment.builder()
                .id(entity.getId())
                .assetId(entity.getAssetId())
                .personId(entity.getPersonId())
                .relationshipTypeId(entity.getRelationshipTypeId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .notes(entity.getNotes())
                .isActive(entity.getIsActive())
                .state(entity.getState() != null ? AssignmentState.valueOf(entity.getState()) : null)
                .acceptanceDate(entity.getAcceptanceDate())
                .pdfPath(entity.getPdfPath())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }
}