package com.datacenter.asset.application.service.company;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepositoryPort locationRepositoryPort;

    public Location create(Location location) {
        if (locationRepositoryPort.existsByCode(location.getCode())) {
            throw new IllegalArgumentException("Ya existe una ubicación con el código: " + location.getCode());
        }
        if (location.getParentLocationId() != null && locationRepositoryPort.findById(location.getParentLocationId()).isEmpty()) {
            throw new IllegalArgumentException("La ubicación padre especificada no existe en el sistema.");
        }

        location.setActive(true);
        return locationRepositoryPort.save(location);
    }

    public Location findById(UUID id) {
        return locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));
    }

    public List<Location> findAll() {
        return locationRepositoryPort.findAll();
    }

    public Location update(UUID id, Location updatedData) {
        Location existing = findById(id);

        if (!existing.getCode().equals(updatedData.getCode()) && locationRepositoryPort.existsByCode(updatedData.getCode())) {
            throw new IllegalArgumentException("Ya existe otra ubicación con el código: " + updatedData.getCode());
        }

        if (updatedData.getParentLocationId() != null) {
            if (updatedData.getParentLocationId().equals(id)) {
                throw new IllegalArgumentException("Una ubicación no puede ser padre de sí misma.");
            }
            if (locationRepositoryPort.findById(updatedData.getParentLocationId()).isEmpty()) {
                throw new IllegalArgumentException("La ubicación padre especificada no existe.");
            }
        }

        existing.setCode(updatedData.getCode());
        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setParentLocationId(updatedData.getParentLocationId());

        return locationRepositoryPort.save(existing);
    }

    public Location activate(UUID id) {
        Location location = findById(id);
        location.setActive(true);
        return locationRepositoryPort.save(location);
    }

    public Location deactivate(UUID id) {
        Location location = findById(id);
        location.setActive(false);
        return locationRepositoryPort.save(location);
    }

    public void delete(UUID id) {
        Location existing = findById(id);
        System.out.println("Eliminando ubicación con código: " + existing.getCode());
        locationRepositoryPort.deleteById(id);
    }
}
