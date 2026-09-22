package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

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
public class UpdateAssetValueRequest {

    private UUID fieldDefinitionId;

    private String value;
}