package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.configuration.SubAssetType;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.SubAssetTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class SubAssetTypePersistenceMapper {

    public SubAssetTypeEntity toEntity(SubAssetType domain) {

        if (domain == null) {
            return null;
        }

        return SubAssetTypeEntity.builder()
                .id(domain.getId())
                .assetTypeId(domain.getAssetTypeId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.isActive())
                .build();
    }

    public SubAssetType toDomain(SubAssetTypeEntity entity) {

        if (entity == null) {
            return null;
        }

        return SubAssetType.builder()
                .id(entity.getId())
                .assetTypeId(entity.getAssetTypeId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.isActive())
                .build();
    }
}