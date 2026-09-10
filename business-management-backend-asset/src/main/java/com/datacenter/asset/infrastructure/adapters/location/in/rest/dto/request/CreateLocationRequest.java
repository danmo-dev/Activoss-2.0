package com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.request;

import java.util.UUID;

public class CreateLocationRequest {
    private UUID parentLocationId;
    private String code;
    private String name;

    public UUID getParentLocationId() { return parentLocationId; }
    public void setParentLocationId(UUID parentLocationId) { this.parentLocationId = parentLocationId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}