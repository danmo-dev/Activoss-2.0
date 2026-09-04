package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import java.util.List;
import java.util.UUID;

public record SubAssetTypeFormResponse(
        UUID subAssetTypeId,
        String subAssetTypeName,
        List<FieldGroupFormResponse> groups
) {}