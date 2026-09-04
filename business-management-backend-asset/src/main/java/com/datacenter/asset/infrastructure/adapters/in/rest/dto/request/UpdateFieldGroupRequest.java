package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFieldGroupRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    private Integer displayOrder;
}