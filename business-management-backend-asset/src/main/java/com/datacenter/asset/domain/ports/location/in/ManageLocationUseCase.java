package com.datacenter.asset.domain.ports.location.in;

import com.datacenter.asset.domain.location.Location;
import java.util.List;
import java.util.UUID;

public interface ManageLocationUseCase {
    Location createLocation(Location location);
    Location getById(UUID id);
    List<Location> getAll();
    Location update(UUID id, Location location);
    Location activate(UUID id);
    Location deactivate(UUID id);
    void delete(UUID id);
}