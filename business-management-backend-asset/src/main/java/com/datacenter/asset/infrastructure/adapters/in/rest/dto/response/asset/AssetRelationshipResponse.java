package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetRelationshipResponse(
        UUID id,
        UUID parentAssetId,
        UUID childAssetId,
        UUID relationshipTypeId,
        LocalDateTime registrationDate
) {}