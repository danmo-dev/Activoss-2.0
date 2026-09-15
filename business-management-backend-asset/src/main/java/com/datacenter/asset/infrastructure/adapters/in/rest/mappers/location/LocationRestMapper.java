package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.location;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.location.CreateLocationRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.location.UpdateLocationRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.location.LocationResponse;

import org.springframework.stereotype.Component;

@Component
public class LocationRestMapper {

    public Location toDomain(CreateLocationRequest request) {
        if (request == null) return null;
        Location domain = new Location();
        domain.setParentLocationId(request.getParentLocationId());
        domain.setCode(request.getCode());
        domain.setName(request.getName());
        return domain;
    }

    // NUEVO MÉTODO PARA EL UPDATE DTO
    public Location toDomain(UpdateLocationRequest request) {
        if (request == null) return null;
        Location domain = new Location();
        domain.setParentLocationId(request.getParentLocationId());
        domain.setCode(request.getCode());
        domain.setName(request.getName());
        return domain;
    }

    public LocationResponse toResponse(Location domain) {
        if (domain == null) return null;
        LocationResponse response = new LocationResponse();
        response.setId(domain.getId());
        response.setParentLocationId(domain.getParentLocationId());
        response.setCode(domain.getCode());
        response.setName(domain.getName());
        return response;
    }
}