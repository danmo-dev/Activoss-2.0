package com.datacenter.asset.domain.owner;

import java.util.UUID;

public class Owner {
    private final UUID id;
    private final UUID companyId;
    private final UUID ownershipTypeId;

    public Owner(UUID id, UUID companyId, UUID ownershipTypeId) {
        this.id = id != null ? id : UUID.randomUUID();
        this.companyId = companyId;
        this.ownershipTypeId = ownershipTypeId;
    }

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public UUID getOwnershipTypeId() { return ownershipTypeId; }
}