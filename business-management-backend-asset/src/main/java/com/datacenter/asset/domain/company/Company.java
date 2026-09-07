package com.datacenter.asset.domain.company;

import java.time.LocalDateTime;
import java.util.UUID;

public class Company {
    private UUID id;
    private String taxId;
    private String name;
    private String companyType;
    private boolean isActive;
    private LocalDateTime createdAt;

    public Company(UUID id, String taxId, String name, String companyType, boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.taxId = taxId;
        this.name = name;
        this.companyType = companyType;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCompanyType() { return companyType; }
    public void setCompanyType(String companyType) { this.companyType = companyType; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}