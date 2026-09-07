package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import java.util.UUID;

public class LocationResponse {
    private UUID id;
    private UUID parentLocationId;
    private String code;
    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getParentLocationId() { return parentLocationId; }
    public void setParentLocationId(UUID parentLocationId) { this.parentLocationId = parentLocationId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}