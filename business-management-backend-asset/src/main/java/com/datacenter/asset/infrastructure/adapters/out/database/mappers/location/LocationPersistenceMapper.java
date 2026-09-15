package com.datacenter.asset.infrastructure.adapters.out.database.mappers.location;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.location.LocationEntity;

import org.springframework.stereotype.Component;

@Component
public class LocationPersistenceMapper {

    public LocationEntity toEntity(Location domain) {
        if (domain == null) return null;
        LocationEntity entity = new LocationEntity();
        entity.setId(domain.getId());
        entity.setParentLocationId(domain.getParentLocationId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        return entity;
    }

    public Location toDomain(LocationEntity entity) {
        if (entity == null) return null;
        Location domain = new Location();
        domain.setId(entity.getId());
        domain.setParentLocationId(entity.getParentLocationId());
        domain.setCode(entity.getCode());
        domain.setName(entity.getName());
        return domain;
    }
}