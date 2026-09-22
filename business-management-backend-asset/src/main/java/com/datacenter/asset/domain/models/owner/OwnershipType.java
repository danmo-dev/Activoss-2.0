package com.datacenter.asset.domain.models.owner;

import java.util.UUID;

public class OwnershipType {
    private final UUID id;
    private String code;
    private String name;

    public OwnershipType() {
        this.id = UUID.randomUUID();
    }

    public OwnershipType(UUID id, String code, String name) {
        this.id = id != null ? id : UUID.randomUUID();
        this.code = code;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }

    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
}