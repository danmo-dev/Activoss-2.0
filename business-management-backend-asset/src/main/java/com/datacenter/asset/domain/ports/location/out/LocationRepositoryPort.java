package com.datacenter.asset.domain.ports.location.out;

import com.datacenter.asset.domain.location.Location;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepositoryPort {
    Location save(Location location);
    Optional<Location> findById(UUID id);
    boolean existsByCode(String code);
}