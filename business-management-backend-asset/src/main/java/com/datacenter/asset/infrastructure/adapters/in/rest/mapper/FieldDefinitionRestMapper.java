package com.datacenter.asset.infrastructure.adapters.in.rest.mapper;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;
import org.springframework.stereotype.Component;

@Component
public class FieldDefinitionRestMapper {

    public FieldDefinitionResponse toResponse(FieldDefinition domain) {
        if (domain == null) {
            return null;
        }

        return new FieldDefinitionResponse(
                domain.getId(),
                domain.getSubAssetTypeId(),
                domain.getFieldGroupId(),
                domain.getName(),
                domain.getLabel(),
                domain.getFieldType(),
                domain.getRequired(),
                domain.getVisible(),
                domain.getEditable(),
                domain.getUnique(),
                domain.getMaxLength(),
                domain.getDisplayOrder(),
                domain.getDefaultValue(),
                domain.getActive()
        );
    }
}