package com.datacenter.asset.infrastructure.adapters.in.rest.mapper;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetResponse;
import org.springframework.stereotype.Component;

@Component
public class AssetRestMapper {

    public Asset toDomain(CreateAssetRequest request) {
        if (request == null) return null;
        return Asset.create(request.getCompanyId(), request.getAssetTypeId(), request.getSubAssetTypeId(),
            request.getOwnershipTypeId(), request.getAssetStatusId(), request.getLocationId(),
            request.getOwnerId(), request.getCode(), request.getName(), request.getDescription(),
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
                .description(domain.getDescription())
                .registrationDate(domain.getRegistrationDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
