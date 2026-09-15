package com.datacenter.asset.domain.models.location;

import java.util.UUID;

public class Location {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private UUID parentLocationId;
    private boolean isActive;

    // 1. CONSTRUCTOR VACÍO (Soluciona el error de los Mappers)
    public Location() {}

    // 2. CONSTRUCTOR CON PARÁMETROS
    public Location(UUID id, String code, String name, String description, UUID parentLocationId, boolean isActive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentLocationId = parentLocationId;
        this.isActive = isActive;
    }

    // Getters y Setters...
    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public UUID getParentLocationId() { return parentLocationId; }
    public boolean isActive() { return isActive; }

    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setParentLocationId(UUID parentLocationId) { this.parentLocationId = parentLocationId; }
    public void setActive(boolean active) { isActive = active; }
}