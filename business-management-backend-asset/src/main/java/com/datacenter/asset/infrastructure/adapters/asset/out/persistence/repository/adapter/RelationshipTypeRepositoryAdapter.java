package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetRelationshipTypeEntity;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetRelationshipTypeJpaRepository;
import com.datacenter.asset.domain.ports.asset.out.RelationshipTypeRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RelationshipTypeRepositoryAdapter
        implements RelationshipTypeRepositoryPort {

    private final AssetRelationshipTypeJpaRepository repository;

    public RelationshipTypeRepositoryAdapter(
            AssetRelationshipTypeJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public AssetRelationshipType save(
            AssetRelationshipType relationshipType
    ) {

        AssetRelationshipTypeEntity entity =
                new AssetRelationshipTypeEntity(
                        relationshipType.getId(),
                        relationshipType.getCode(),
                        relationshipType.getName()
                );

        entity = repository.save(entity);

        return new AssetRelationshipType(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        );
    }

    @Override
    public List<AssetRelationshipType> findAll() {

        return repository.findAll()
                .stream()
                .map(entity -> new AssetRelationshipType(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName()
                ))
                .collect(Collectors.toList());
    }
}