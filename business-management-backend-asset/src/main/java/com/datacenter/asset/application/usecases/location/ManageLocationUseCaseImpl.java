package com.datacenter.asset.application.usecases.location;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.domain.ports.in.location.ManageLocationUseCase;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageLocationUseCaseImpl implements ManageLocationUseCase {

    private final LocationRepositoryPort locationRepositoryPort;

    @Override
    @Transactional
    public Location createLocation(Location location) {
        // Lógica restaurada: Validar código duplicado
        if (locationRepositoryPort.existsByCode(location.getCode())) {
            throw new BusinessException("Ya existe una ubicación con el código: " + location.getCode());
        }
        // Lógica restaurada: Validar que el padre exista
        if (location.getParentLocationId() != null && locationRepositoryPort.findById(location.getParentLocationId()).isEmpty()) {
            throw new BusinessException("La ubicación padre especificada no existe en el sistema.");
        }

        location.setActive(true);
        return locationRepositoryPort.save(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Location getById(UUID id) {
        return locationRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAll() {
        return locationRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Location update(UUID id, Location updatedData) {
        Location existing = getById(id);

        // Lógica restaurada: Validar que el nuevo código no choque con otro existente
        if (!existing.getCode().equals(updatedData.getCode()) && locationRepositoryPort.existsByCode(updatedData.getCode())) {
            throw new BusinessException("Ya existe otra ubicación con el código: " + updatedData.getCode());
        }

        // Lógica restaurada: Validaciones de ubicación padre
        if (updatedData.getParentLocationId() != null) {
            if (updatedData.getParentLocationId().equals(id)) {
                throw new BusinessException("Una ubicación no puede ser padre de sí misma.");
            }
            if (locationRepositoryPort.findById(updatedData.getParentLocationId()).isEmpty()) {
                throw new BusinessException("La ubicación padre especificada no existe.");
            }
        }

        // Propiedades restauradas a las originales del dominio (nada de Address o City)
        existing.setCode(updatedData.getCode());
        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setParentLocationId(updatedData.getParentLocationId());

        return locationRepositoryPort.save(existing);
    }

    @Override
    @Transactional
    public Location activate(UUID id) {
        Location location = getById(id);
        location.setActive(true);
        return locationRepositoryPort.save(location);
    }

    @Override
    @Transactional
    public Location deactivate(UUID id) {
        Location location = getById(id);
        location.setActive(false);
        return locationRepositoryPort.save(location);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Location existing = getById(id);
        locationRepositoryPort.deleteById(existing.getId());
    }
}