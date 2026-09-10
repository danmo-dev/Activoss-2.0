package com.datacenter.asset.domain.configuration;

import java.util.UUID;

public class OwnershipType {
    private final UUID id;
    private final String code;
    private final String name;

    public OwnershipType(UUID id, String code, String name) {
        this.id = id != null ? id : UUID.randomUUID();
        this.code = code;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
}