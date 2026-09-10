package com.datacenter.asset.infrastructure.adapters.asset.in.rest.mapper;

import com.datacenter.asset.domain.configuration.AssetType;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request.CreateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.response.AssetTypeResponse;

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