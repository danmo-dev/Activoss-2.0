package com.datacenter.asset.domain.configuration;

import java.util.UUID;

public class AssetRelationshipType {
    private UUID id;
    private String code;
    private String name;

    public AssetRelationshipType() {}

    public AssetRelationshipType(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }

    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
}