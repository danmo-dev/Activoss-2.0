package com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetRelationshipEntity;

import org.springframework.stereotype.Component;

@Component
public class AssetRelationshipMapper {

    public AssetRelationshipEntity toEntity(AssetRelationship domain) {
        if (domain == null) return null;
        return AssetRelationshipEntity.builder()
                .id(domain.getId())
                .parentAssetId(domain.getParentAssetId())
                .childAssetId(domain.getChildAssetId())
                .relationshipTypeId(domain.getRelationshipTypeId())
                .registrationDate(domain.getRegistrationDate())
                .build();
    }

    public AssetRelationship toDomain(AssetRelationshipEntity entity) {
        if (entity == null) return null;
        return AssetRelationship.builder()
                .id(entity.getId())
                .parentAssetId(entity.getParentAssetId())
                .childAssetId(entity.getChildAssetId())
                .relationshipTypeId(entity.getRelationshipTypeId())
                .registrationDate(entity.getRegistrationDate())
                .build();
    }
}
