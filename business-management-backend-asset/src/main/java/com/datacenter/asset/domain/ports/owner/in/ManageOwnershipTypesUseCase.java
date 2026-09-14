package com.datacenter.asset.domain.ports.owner.in;

import com.datacenter.asset.domain.owner.OwnershipType;
import java.util.List;
import java.util.UUID;

public interface ManageOwnershipTypesUseCase {
    OwnershipType createOwnershipType(String code, String name);
    OwnershipType getById(UUID id);
    List<OwnershipType> getAllOwnershipTypes();
    OwnershipType update(UUID id, String code, String name);
    void delete(UUID id);
}