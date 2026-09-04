package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import java.util.UUID;

public record FieldDefinitionResponse(
        UUID id,
        UUID subAssetTypeId,
        UUID fieldGroupId,
        String name,
        String label,
        String fieldType,
        Boolean required,
        Boolean visible,
        Boolean editable,
        Boolean unique,
        Integer maxLength,
        Integer displayOrder,
        String defaultValue,
        Boolean active
) {
}