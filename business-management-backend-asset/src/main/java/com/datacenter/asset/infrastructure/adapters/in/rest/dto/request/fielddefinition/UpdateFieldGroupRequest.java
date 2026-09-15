package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.fielddefinition;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFieldGroupRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    private Integer displayOrder;

    @NotNull(message = "Sub Asset Type ID is required")
    private UUID subAssetTypeId;
}