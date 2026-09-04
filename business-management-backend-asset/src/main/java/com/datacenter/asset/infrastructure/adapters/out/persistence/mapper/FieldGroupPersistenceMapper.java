package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldGroupEntity;
import org.springframework.stereotype.Component;

@Component
public class FieldGroupPersistenceMapper {

    public FieldGroupEntity toEntity(FieldGroup domain) {

        FieldGroupEntity entity = new FieldGroupEntity();

        entity.setId(domain.getId());
        entity.setSubAssetTypeId(domain.getSubAssetTypeId());
        entity.setName(domain.getName());
        entity.setDisplayOrder(domain.getDisplayOrder());
        entity.setActive(domain.getActive());

        return entity;
    }

    public FieldGroup toDomain(FieldGroupEntity entity) {

        return new FieldGroup(
                entity.getId(),
                entity.getSubAssetTypeId(),
                entity.getName(),
                entity.getDisplayOrder(),
                entity.getActive()
        );
    }
}