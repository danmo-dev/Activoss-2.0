package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetPersistenceMapper {

    public AssetEntity toEntity(Asset domain) {
        if (domain == null) return null;
        return AssetEntity.builder()
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

    public Asset toDomain(AssetEntity entity) {
        if (entity == null) return null;
        return Asset.restore(new AssetId(entity.getId()), entity.getCompanyId(), entity.getAssetTypeId(),
            entity.getSubAssetTypeId(), entity.getOwnershipTypeId(), entity.getAssetStatusId(),
            entity.getLocationId(), entity.getOwnerId(), AssetCode.of(entity.getCode()), entity.getName(),
            entity.getDescription(), entity.getRegistrationDate(), null, entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
