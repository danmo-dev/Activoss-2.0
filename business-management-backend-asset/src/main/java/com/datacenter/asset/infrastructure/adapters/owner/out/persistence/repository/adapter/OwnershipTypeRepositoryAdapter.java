package com.datacenter.asset.infrastructure.adapters.owner.out.persistence.repository.adapter;

import com.datacenter.asset.infrastructure.adapters.owner.out.persistence.entity.OwnershipTypeEntity;
import com.datacenter.asset.infrastructure.adapters.owner.out.persistence.repository.jpa.OwnershipTypeJpaRepository;
import com.datacenter.asset.domain.owner.OwnershipType;
import com.datacenter.asset.domain.ports.owner.out.OwnershipTypeRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnershipTypeRepositoryAdapter
        implements OwnershipTypeRepositoryPort {

    private final OwnershipTypeJpaRepository repository;

    public OwnershipTypeRepositoryAdapter(
            OwnershipTypeJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public OwnershipType save(
            OwnershipType ownershipType
    ) {

        OwnershipTypeEntity entity =
                new OwnershipTypeEntity(
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
    public List<OwnershipType> findAll() {

        return repository.findAll()
                .stream()
                .map(entity -> new OwnershipType(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName()
                ))
                .collect(Collectors.toList());
    }
}