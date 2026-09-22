package com.datacenter.asset.infrastructure.adapters.out.database.adapters.location;

import com.datacenter.asset.domain.models.location.Location;
import com.datacenter.asset.domain.ports.out.location.LocationRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.location.LocationPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.location.LocationJpaRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Location> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}