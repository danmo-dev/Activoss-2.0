package com.datacenter.asset.infrastructure.adapters.in.rest.mapper;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.AssignAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssignmentResponse;
import org.springframework.stereotype.Component;

@Component
public class AssetAssignmentRestMapper {

    public AssetAssignment toDomain(AssignAssetRequest request) {
        if (request == null) return null;
        return AssetAssignment.builder()
                .assetId(request.getAssetId())
                .personId(request.getPersonId())
                .notes(request.getNotes())
                .build();
    }

    public AssignmentResponse toResponse(AssetAssignment domain) {
        if (domain == null) return null;
        return AssignmentResponse.builder()
                .id(domain.getId())
                .assetId(domain.getAssetId())
                .personId(domain.getPersonId())
                .state(domain.getState() != null ? domain.getState().name() : null)
                .startDate(domain.getStartDate())
                .pdfPath(domain.getPdfPath())
                .build();
    }
}