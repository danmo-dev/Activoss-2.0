package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldDefinitionEntity;
import org.springframework.stereotype.Component;

@Component
public class FieldDefinitionPersistenceMapper {

    public FieldDefinitionEntity toEntity(FieldDefinition domain) {
        if (domain == null) return null;

        FieldDefinitionEntity entity = new FieldDefinitionEntity();

        entity.setId(domain.getId());
        entity.setSubAssetTypeId(domain.getSubAssetTypeId());
        entity.setFieldGroupId(domain.getFieldGroupId());
        entity.setName(domain.getName());
        entity.setLabel(domain.getLabel());
        entity.setFieldType(domain.getFieldType());
        entity.setRequired(domain.getRequired());
        entity.setVisible(domain.getVisible());
        entity.setEditable(domain.getEditable());
        entity.setUnique(domain.getUnique());
        entity.setMaxLength(domain.getMaxLength());
        entity.setDisplayOrder(domain.getDisplayOrder());
        entity.setDefaultValue(domain.getDefaultValue());
        entity.setActive(domain.getActive());

        return entity;
    }

    public FieldDefinition toDomain(FieldDefinitionEntity entity) {
        if (entity == null) return null;

        return new FieldDefinition(
                entity.getId(),
                entity.getSubAssetTypeId(),
                entity.getFieldGroupId(),
                entity.getName(),
                entity.getLabel(),
                entity.getFieldType(),
                entity.getRequired(),
                entity.getVisible(),
                entity.getEditable(),
                entity.getUnique(),
                entity.getMaxLength(),
                entity.getDisplayOrder(),
                entity.getDefaultValue(),
                entity.getActive()
        );
    }
}