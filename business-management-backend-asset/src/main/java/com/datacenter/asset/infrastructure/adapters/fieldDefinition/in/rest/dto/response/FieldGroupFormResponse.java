package com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.response;

import java.util.List;
import java.util.UUID;

public record FieldGroupFormResponse(
        UUID id,
        String name,
        Integer displayOrder,
        List<FieldDefinitionResponse> fields
) {}