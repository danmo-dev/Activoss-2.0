package com.datacenter.asset.domain.ports.owner.out;

import java.util.List;
import java.util.UUID;

import com.datacenter.asset.domain.owner.Owner;

public interface OwnerRepositoryPort {

    Owner save(Owner owner);

    List<Owner> findByCompanyId(UUID companyId);
}