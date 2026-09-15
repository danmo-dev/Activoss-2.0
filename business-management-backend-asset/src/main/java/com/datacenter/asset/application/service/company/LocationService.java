package com.datacenter.asset.application.service.company;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.domain.ports.in.location.ManageLocationUseCase;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService implements ManageLocationUseCase {

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
        
        location.setActive(true); // Por defecto activo al crear
        return locationRepositoryPort.save(location);
    }

    @Override
    public Location getById(UUID id) {
        return locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));
    }

    @Override
    public List<Location> getAll() {
        return locationRepositoryPort.findAll();
    }

    @Override
    public Location update(UUID id, Location updatedData) {
        Location existing = locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));

        // 1. Validar si cambiaron el código y si ya existe
        if (!existing.getCode().equals(updatedData.getCode()) && locationRepositoryPort.existsByCode(updatedData.getCode())) {
            throw new IllegalArgumentException("Ya existe otra ubicación con el código: " + updatedData.getCode());
        }

        // 2. Validar si cambiaron el parentLocationId
        if (updatedData.getParentLocationId() != null) {
            // Evitar que sea padre de sí misma
            if (updatedData.getParentLocationId().equals(id)) {
                throw new IllegalArgumentException("Una ubicación no puede ser padre de sí misma.");
            }
            if (locationRepositoryPort.findById(updatedData.getParentLocationId()).isEmpty()) {
                throw new IllegalArgumentException("La ubicación padre especificada no existe.");
            }
        }

        // 3. Actualizar datos
        existing.setCode(updatedData.getCode());
        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setParentLocationId(updatedData.getParentLocationId());

        return locationRepositoryPort.save(existing);
    }

    @Override
    public Location activate(UUID id) {
        Location location = locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));
        location.setActive(true);
        return locationRepositoryPort.save(location);
    }

    @Override
    public Location deactivate(UUID id) {
        Location location = locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));
        location.setActive(false);
        return locationRepositoryPort.save(location);
    }

    @Override
    public void delete(UUID id) {
        Location existing = locationRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada con id: " + id));
        System.out.println("Eliminando estado con código: " + existing.getCode());
        locationRepositoryPort.deleteById(id);
    }
}