package com.datacenter.asset.domain.owner;

import java.util.UUID;

public class Owner {
    private final UUID id;
    private UUID companyId;
    private UUID ownershipTypeId;

    public Owner() {
        this.id = UUID.randomUUID();
    }

    public Owner(UUID id, UUID companyId, UUID ownershipTypeId) {
        this.id = id != null ? id : UUID.randomUUID();
        this.companyId = companyId;
        this.ownershipTypeId = ownershipTypeId;
    }

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public UUID getOwnershipTypeId() { return ownershipTypeId; }

    public void setCompanyId(UUID companyId) { this.companyId = companyId; }
    public void setOwnershipTypeId(UUID ownershipTypeId) { this.ownershipTypeId = ownershipTypeId; }
}