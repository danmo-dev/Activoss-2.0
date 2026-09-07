package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class AssignAssetRequest {
    private UUID assetId;
    private UUID personId;
    private String notes;
}