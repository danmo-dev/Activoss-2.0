package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFieldGroupRequest {

    @NotNull(message = "Sub Asset Type ID is required")
    private UUID subAssetTypeId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    private Integer displayOrder;
}