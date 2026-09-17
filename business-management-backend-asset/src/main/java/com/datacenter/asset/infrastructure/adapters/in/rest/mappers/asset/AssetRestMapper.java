package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.CreateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssetResponse;

import org.springframework.stereotype.Component;

@Component
public class AssetRestMapper {

    public Asset toDomain(CreateAssetRequest request) {
        if (request == null) return null;
        return Asset.create(request.getCompanyId(), request.getAssetTypeId(), request.getSubAssetTypeId(),
            request.getOwnershipTypeId(), request.getAssetStatusId(), request.getLocationId(),
            request.getOwnerId(), request.getCode(), request.getName(),request.getDescription(),
            request.getRegistrationDate());
    }

    public AssetResponse toResponse(Asset domain) {
        if (domain == null) return null;
        return AssetResponse.builder()
                .id(domain.getId().value())
                .companyId(domain.getCompanyId())
                .assetTypeId(domain.getAssetTypeId())
                .subAssetTypeId(domain.getSubAssetTypeId())
                .ownershipTypeId(domain.getOwnershipTypeId())
                .assetStatusId(domain.getAssetStatusId())
                .locationId(domain.getLocationId())
                .ownerId(domain.getOwnerId())
                .code(domain.getCode().value())
                .name(domain.getName())
                .isActive(domain.getIsActive())
                .description(domain.getDescription())
                .registrationDate(domain.getRegistrationDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
