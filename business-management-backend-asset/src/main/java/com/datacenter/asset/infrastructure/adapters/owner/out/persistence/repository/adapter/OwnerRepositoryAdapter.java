package com.datacenter.asset.infrastructure.adapters.owner.out.persistence.repository.adapter;

import com.datacenter.asset.infrastructure.adapters.owner.out.persistence.entity.OwnerEntity;
import com.datacenter.asset.infrastructure.adapters.owner.out.persistence.repository.jpa.OwnerJpaRepository;
import com.datacenter.asset.domain.owner.Owner;
import com.datacenter.asset.domain.ports.owner.out.OwnerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OwnerRepositoryAdapter
        implements OwnerRepositoryPort {

    private final OwnerJpaRepository repository;

    public OwnerRepositoryAdapter(
            OwnerJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Owner save(Owner owner) {

        OwnerEntity entity =
                new OwnerEntity(
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
    public List<Owner> findByCompanyId(
            UUID companyId
    ) {

        return repository.findByCompanyId(companyId)
                .stream()
                .map(entity -> new Owner(
                        entity.getId(),
                        entity.getCompanyId(),
                        entity.getOwnershipTypeId()
                ))
                .collect(Collectors.toList());
    }
}
