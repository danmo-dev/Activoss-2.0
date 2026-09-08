package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.domain.ports.in.CreateLocationUseCase;
import com.datacenter.asset.domain.ports.out.LocationRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements CreateLocationUseCase {

    private final LocationRepositoryPort locationRepositoryPort;

    public LocationService(LocationRepositoryPort locationRepositoryPort) {
        this.locationRepositoryPort = locationRepositoryPort;
    }

    @Override
    public Location createLocation(Location location) {
        if (locationRepositoryPort.existsByCode(location.getCode())) {
            throw new IllegalArgumentException("Ya existe una ubicación con el código: " + location.getCode());
        }
        if (location.getParentLocationId() != null && locationRepositoryPort.findById(location.getParentLocationId()).isEmpty()) {
            throw new IllegalArgumentException("La ubicación padre especificada no existe en el sistema.");
        }
        return locationRepositoryPort.save(location);
    }
}
