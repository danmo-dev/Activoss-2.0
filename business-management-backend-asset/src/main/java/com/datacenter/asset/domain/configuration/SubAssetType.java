package com.datacenter.asset.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAssetType {

    private UUID id;

    private UUID assetTypeId;

    private String code;

    private String name;

    private String description;

    private boolean active;
}