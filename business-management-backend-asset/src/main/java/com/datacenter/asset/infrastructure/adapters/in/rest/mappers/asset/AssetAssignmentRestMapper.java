package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssignmentResponse;

import org.springframework.stereotype.Component;

@Component
public class AssetAssignmentRestMapper {

    public AssignmentResponse toResponse(AssetAssignment domain) {
        if (domain == null) return null;
        return AssignmentResponse.builder()
                .id(domain.getId())
                .assetId(domain.getAssetId())
                .personId(domain.getPersonId())
                .batchId(domain.getBatchId())
                .state(domain.getState() != null ? domain.getState().name() : null)
                .startDate(domain.getStartDate())
                .pdfPath(domain.getPdfPath())
                .build();
    }
}