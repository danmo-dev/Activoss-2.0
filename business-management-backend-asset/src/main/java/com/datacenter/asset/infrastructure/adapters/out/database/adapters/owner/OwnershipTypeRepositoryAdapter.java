package com.datacenter.asset.infrastructure.adapters.out.database.adapters.owner;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.owner.OwnershipTypeEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.owner.OwnershipTypeJpaRepository;
import com.datacenter.asset.domain.models.owner.OwnershipType;
import com.datacenter.asset.domain.ports.out.owner.OwnershipTypeRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
public class OwnershipTypeRepositoryAdapter implements OwnershipTypeRepositoryPort {

    private final OwnershipTypeJpaRepository repository;

    public OwnershipTypeRepositoryAdapter(OwnershipTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OwnershipType save(OwnershipType ownershipType) {
        OwnershipTypeEntity entity = new OwnershipTypeEntity(
                ownershipType.getId(),
                ownershipType.getCode(),
                ownershipType.getName()
        );

        entity = repository.save(entity);

        return new OwnershipType(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        );
    }

    @Override
    public Optional<OwnershipType> findById(UUID id) {
        return repository.findById(id).map(entity -> new OwnershipType(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        ));
    }

    @Override
    public List<OwnershipType> findAll() {
        return repository.findAll().stream()
                .map(entity -> new OwnershipType(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName()
                ))
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