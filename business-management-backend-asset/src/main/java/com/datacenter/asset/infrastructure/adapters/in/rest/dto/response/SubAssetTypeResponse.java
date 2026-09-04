package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAssetTypeResponse {

    private UUID id;

    private UUID assetTypeId;

    private String code;

    private String name;

    private String description;

    private boolean active;
}