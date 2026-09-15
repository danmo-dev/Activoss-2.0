package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.subasset;

import com.datacenter.asset.domain.models.configuration.SubAssetType;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.subasset.CreateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.subasset.SubAssetTypeResponse;

import org.springframework.stereotype.Component;

@Component
public class SubAssetTypeRestMapper {

    public SubAssetType toDomain(
            CreateSubAssetTypeRequest request
    ) {

        if (request == null) {
            return null;
        }

        return SubAssetType.builder()
                .assetTypeId(request.getAssetTypeId())
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .active(true)
                .build();
    }

    public SubAssetTypeResponse toResponse(
            SubAssetType domain
    ) {

        if (domain == null) {
            return null;
        }

        return SubAssetTypeResponse.builder()
                .id(domain.getId())
                .assetTypeId(domain.getAssetTypeId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.isActive())
                .build();
    }
}