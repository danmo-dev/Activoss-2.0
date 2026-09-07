package com.datacenter.asset.domain.asset;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AssetHistory {
    private UUID id;
    private UUID assetId;
    private LocalDateTime eventDate;
    private String eventType;
    private String executedBy;
    private String description;
}