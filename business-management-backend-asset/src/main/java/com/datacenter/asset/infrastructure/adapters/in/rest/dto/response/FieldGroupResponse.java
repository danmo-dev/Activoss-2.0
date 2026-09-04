package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FieldGroupResponse {

    private UUID id;
    private UUID subAssetTypeId;
    private String name;
    private Integer displayOrder;
    private Boolean active;
}