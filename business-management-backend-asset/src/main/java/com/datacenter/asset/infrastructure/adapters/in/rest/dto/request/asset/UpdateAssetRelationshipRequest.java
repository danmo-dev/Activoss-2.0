package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

import java.util.UUID;

public record UpdateAssetRelationshipRequest(
        UUID parentAssetId,
        UUID childAssetId,
        UUID relationshipTypeId,
        String modifiedBy
) {}