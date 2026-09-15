package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.subasset;

import java.util.List;
import java.util.UUID;

import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.fielddefinition.FieldGroupFormResponse;

public record SubAssetTypeFormResponse(
        UUID subAssetTypeId,
        String subAssetTypeName,
        List<FieldGroupFormResponse> groups
) {}