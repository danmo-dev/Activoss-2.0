package com.datacenter.asset.infrastructure.adapters.out.persistence.mapper;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.LocationEntity;
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

        return new Location(
                entity.getId(),
                entity.getParentLocationId(),
                entity.getCode(),
                entity.getName()
        );
    }
}
