package com.datacenter.asset.domain.ports.owner.out;

import com.datacenter.asset.domain.owner.OwnershipType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnershipTypeRepositoryPort {
    OwnershipType save(OwnershipType ownershipType);
    Optional<OwnershipType> findById(UUID id);
    List<OwnershipType> findAll();
    boolean existsByCode(String code);
    void deleteById(UUID id);
}