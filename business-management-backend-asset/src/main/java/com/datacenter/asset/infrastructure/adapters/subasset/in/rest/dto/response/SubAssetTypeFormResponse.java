package com.datacenter.asset.infrastructure.adapters.subasset.in.rest.dto.response;

import java.util.List;
import java.util.UUID;

import com.datacenter.asset.infrastructure.adapters.fieldDefinition.in.rest.dto.response.FieldGroupFormResponse;

public record SubAssetTypeFormResponse(
        UUID subAssetTypeId,
        String subAssetTypeName,
        List<FieldGroupFormResponse> groups
) {}