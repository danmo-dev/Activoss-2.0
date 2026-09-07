package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class AssetValueResponse {
    private UUID id;
    private UUID assetId;
    private UUID fieldDefinitionId;
    private String value;
}