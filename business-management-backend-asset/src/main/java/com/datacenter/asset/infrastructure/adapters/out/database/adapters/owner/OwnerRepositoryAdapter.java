package com.datacenter.asset.infrastructure.adapters.out.database.adapters.owner;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.owner.OwnerEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.owner.OwnerJpaRepository;
import com.datacenter.asset.domain.models.owner.Owner;
import com.datacenter.asset.domain.ports.out.owner.OwnerRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
public class OwnerRepositoryAdapter implements OwnerRepositoryPort {

    private final OwnerJpaRepository repository;

    public OwnerRepositoryAdapter(OwnerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = new OwnerEntity(
                owner.getId(),
                owner.getCompanyId(),
                owner.getOwnershipTypeId()
        );

        entity = repository.save(entity);

        return new Owner(
                entity.getId(),
                entity.getCompanyId(),
                entity.getOwnershipTypeId()
        );
    }

    @Override
    public Optional<Owner> findById(UUID id) {
        return repository.findById(id).map(entity -> new Owner(
                entity.getId(),
                entity.getCompanyId(),
                entity.getOwnershipTypeId()
        ));
    }

    @Override
    public List<Owner> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Owner(
                        entity.getId(),
                        entity.getCompanyId(),
                        entity.getOwnershipTypeId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Owner> findByCompanyId(UUID companyId) {
        return repository.findByCompanyId(companyId).stream()
                .map(entity -> new Owner(
                        entity.getId(),
                        entity.getCompanyId(),
                        entity.getOwnershipTypeId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}