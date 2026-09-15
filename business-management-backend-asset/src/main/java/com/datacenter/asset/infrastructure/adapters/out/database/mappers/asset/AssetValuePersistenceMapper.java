package com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetValueEntity;

import org.springframework.stereotype.Component;

@Component
public class AssetValuePersistenceMapper {
    public AssetValueEntity toEntity(AssetValue domain) {
        if (domain == null) return null;
        AssetValueEntity entity = new AssetValueEntity();
        entity.setId(domain.getId());
        entity.setAssetId(domain.getAssetId());
        entity.setFieldDefinitionId(domain.getFieldDefinitionId());
        entity.setValue(domain.getValue());
        return entity;
    }

    public AssetValue toDomain(AssetValueEntity entity) {
        if (entity == null) return null;
        return AssetValue.builder()
                .id(entity.getId())
                .assetId(entity.getAssetId())
                .fieldDefinitionId(entity.getFieldDefinitionId())
                .value(entity.getValue())
                .build();
    }
}