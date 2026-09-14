package com.datacenter.asset.domain.ports.location.out;

import com.datacenter.asset.domain.location.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepositoryPort {
    Location save(Location location);
    Optional<Location> findById(UUID id);
    List<Location> findAll();
    boolean existsByCode(String code);
    void deleteById(UUID id);
}