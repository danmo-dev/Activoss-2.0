package com.datacenter.asset.domain.ports.owner.out;

import java.util.List;

import com.datacenter.asset.domain.owner.OwnershipType;

public interface OwnershipTypeRepositoryPort {

    OwnershipType save(OwnershipType ownershipType);

    List<OwnershipType> findAll();
}
