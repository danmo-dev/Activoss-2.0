package com.datacenter.asset.infrastructure.adapters.location.out.persistence.repository.adapter;

import com.datacenter.asset.domain.location.Location;
import com.datacenter.asset.domain.ports.location.out.LocationRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.location.out.persistence.mapper.LocationPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.location.out.persistence.repository.jpa.LocationJpaRepository;

import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("null")
@Component
public class LocationRepositoryAdapter implements LocationRepositoryPort {

    private final LocationJpaRepository repository;
    private final LocationPersistenceMapper mapper;

    public LocationRepositoryAdapter(LocationJpaRepository repository, LocationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Location save(Location location) {
        return mapper.toDomain(repository.save(mapper.toEntity(location)));
    }

    @Override
    public Optional<Location> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}