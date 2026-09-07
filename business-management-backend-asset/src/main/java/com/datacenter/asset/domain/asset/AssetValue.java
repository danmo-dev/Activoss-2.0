package com.datacenter.asset.domain.asset;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class AssetValue {
    private UUID id;
    private UUID assetId;
    private UUID fieldDefinitionId;
    private String value;
}