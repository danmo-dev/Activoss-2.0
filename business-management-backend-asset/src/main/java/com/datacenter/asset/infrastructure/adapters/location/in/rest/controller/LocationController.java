package com.datacenter.asset.infrastructure.adapters.location.in.rest.controller;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.domain.ports.location.in.ManageLocationUseCase;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.request.CreateLocationRequest;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.request.UpdateLocationRequest; // (Crea este DTO si no existe)
import com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.response.LocationResponse;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.mapper.LocationRestMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final ManageLocationUseCase manageLocationUseCase;
    private final LocationRestMapper mapper;

    public LocationController(ManageLocationUseCase manageLocationUseCase, LocationRestMapper mapper) {
        this.manageLocationUseCase = manageLocationUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody CreateLocationRequest request) {
        Location location = mapper.toDomain(request);
        Location createdLocation = manageLocationUseCase.createLocation(location);
        return new ResponseEntity<>(mapper.toResponse(createdLocation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> responseList = manageLocationUseCase.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable UUID id) {
        Location location = manageLocationUseCase.getById(id);
        return ResponseEntity.ok(mapper.toResponse(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable UUID id, 
            @RequestBody UpdateLocationRequest request) { // Asegúrate de tener este DTO creado en tu carpeta dto/request
        Location domainRequest = mapper.toDomain(request);
        Location updatedLocation = manageLocationUseCase.update(id, domainRequest);
        return ResponseEntity.ok(mapper.toResponse(updatedLocation));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<LocationResponse> activate(@PathVariable UUID id) {
        Location activated = manageLocationUseCase.activate(id);
        return ResponseEntity.ok(mapper.toResponse(activated));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<LocationResponse> deactivate(@PathVariable UUID id) {
        Location deactivate = manageLocationUseCase.deactivate(id);
        return ResponseEntity.ok(mapper.toResponse(deactivate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        manageLocationUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}