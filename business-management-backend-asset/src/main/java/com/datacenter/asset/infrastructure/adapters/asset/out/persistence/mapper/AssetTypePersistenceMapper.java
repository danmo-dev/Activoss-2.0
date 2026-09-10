package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper;

import com.datacenter.asset.domain.configuration.AssetType;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetTypeEntity;

import org.springframework.stereotype.Component;

@Component
public class AssetTypePersistenceMapper {

    public AssetTypeEntity toEntity(AssetType domain) {

        if (domain == null) {
            return null;
        }

        return AssetTypeEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.isActive())
                .build();
    }

    public AssetType toDomain(AssetTypeEntity entity) {

        if (entity == null) {
            return null;
        }

        return AssetType.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.isActive())
                .build();
    }
}