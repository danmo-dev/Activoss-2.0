package com.datacenter.asset.application.usecases.company;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.domain.ports.in.location.ManageLocationUseCase;
import com.datacenter.asset.application.service.company.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageLocationUseCaseImpl implements ManageLocationUseCase {

    private final LocationService locationService;

    @Override
    @Transactional
    public Location createLocation(Location location) {
        return locationService.create(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Location getById(UUID id) {
        return locationService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAll() {
        return locationService.findAll();
    }

    @Override
    @Transactional
    public Location update(UUID id, Location location) {
        return locationService.update(id, location);
    }

    @Override
    @Transactional
    public Location activate(UUID id) {
        return locationService.activate(id);
    }

    @Override
    @Transactional
    public Location deactivate(UUID id) {
        return locationService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        locationService.delete(id);
    }
}