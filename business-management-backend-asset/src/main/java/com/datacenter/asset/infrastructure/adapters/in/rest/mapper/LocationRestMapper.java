package com.datacenter.asset.infrastructure.adapters.in.rest.mapper;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateLocationRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.LocationResponse;
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
