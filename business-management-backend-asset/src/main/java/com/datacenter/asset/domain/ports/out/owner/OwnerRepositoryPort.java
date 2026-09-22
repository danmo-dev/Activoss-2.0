package com.datacenter.asset.domain.ports.out.owner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.datacenter.asset.domain.models.owner.Owner;

public interface OwnerRepositoryPort {

    Owner save(Owner owner);

    Optional<Owner> findById(UUID id);

    List<Owner> findAll();

    List<Owner> findByCompanyId(UUID companyId);

    void deleteById(UUID id);
}