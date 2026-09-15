package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.CreateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssetTypeResponse;

import org.springframework.stereotype.Component;

@Component
public class AssetTypeRestMapper {

    public AssetType toDomain(CreateAssetTypeRequest request) {

        if (request == null) {
            return null;
        }

        return AssetType.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .active(true)
                .build();
    }

    public AssetTypeResponse toResponse(AssetType domain) {

        if (domain == null) {
            return null;
        }

        return AssetTypeResponse.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.isActive())
                .build();
    }
}