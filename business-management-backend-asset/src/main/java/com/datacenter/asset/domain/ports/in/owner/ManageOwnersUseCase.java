package com.datacenter.asset.domain.ports.in.owner;

import java.util.List;
import java.util.UUID;

import com.datacenter.asset.domain.models.owner.Owner;

public interface ManageOwnersUseCase {

    Owner createOwner(UUID companyId, UUID ownershipTypeId);
    
    Owner getById(UUID id);
    
    List<Owner> getAllOwners();
    
    List<Owner> getOwnersByCompany(UUID companyId);
    
    Owner update(UUID id, UUID companyId, UUID ownershipTypeId);
    
    void delete(UUID id);
}