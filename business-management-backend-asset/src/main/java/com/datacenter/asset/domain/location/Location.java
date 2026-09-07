package com.datacenter.asset.domain.location;

import java.util.UUID;

public class Location {
    private UUID id;
    private UUID parentLocationId;
    private String code;
    private String name;

    public Location() {}
    
    public Location(UUID id, UUID parentLocationId, String code, String name) {
        this.id = id;
        this.parentLocationId = parentLocationId;
        this.code = code;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getParentLocationId() { return parentLocationId; }
    public void setParentLocationId(UUID parentLocationId) { this.parentLocationId = parentLocationId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}