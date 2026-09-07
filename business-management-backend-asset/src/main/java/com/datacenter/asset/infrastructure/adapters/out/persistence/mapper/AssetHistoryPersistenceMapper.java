package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.asset.AssetHistory;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryPersistenceMapper {

    public AssetHistoryEntity toEntity(AssetHistory domain) {
        if (domain == null) return null;
        AssetHistoryEntity entity = new AssetHistoryEntity();
        entity.setId(domain.getId());
        entity.setAssetId(domain.getAssetId());
        entity.setEventDate(domain.getEventDate());
        entity.setEventType(domain.getEventType());
        entity.setExecutedBy(domain.getExecutedBy());
        entity.setDescription(domain.getDescription());
        return entity;
    }

    public AssetHistory toDomain(AssetHistoryEntity entity) {
        if (entity == null) return null;
        return AssetHistory.builder()
                .id(entity.getId())
                .assetId(entity.getAssetId())
                .eventDate(entity.getEventDate())
                .eventType(entity.getEventType())
                .executedBy(entity.getExecutedBy())
                .description(entity.getDescription())
                .build();
    }
}