package com.datacenter.asset.infrastructure.adapters.location.in.rest.controller;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.domain.ports.location.in.CreateLocationUseCase;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.request.CreateLocationRequest;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.dto.response.LocationResponse;
import com.datacenter.asset.infrastructure.adapters.location.in.rest.mapper.LocationRestMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final CreateLocationUseCase createLocationUseCase;
    private final LocationRestMapper mapper;

    public LocationController(CreateLocationUseCase createLocationUseCase, LocationRestMapper mapper) {
        this.createLocationUseCase = createLocationUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody CreateLocationRequest request) {
        Location location = mapper.toDomain(request);
        Location createdLocation = createLocationUseCase.createLocation(location);
        return new ResponseEntity<>(mapper.toResponse(createdLocation), HttpStatus.CREATED);
    }
}