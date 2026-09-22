package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.out.asset.RelationshipTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetRelationshipTypeEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetRelationshipTypeJpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
public class RelationshipTypeRepositoryAdapter implements RelationshipTypeRepositoryPort {

    private final AssetRelationshipTypeJpaRepository repository;

    public RelationshipTypeRepositoryAdapter(AssetRelationshipTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AssetRelationshipType save(AssetRelationshipType relationshipType) {
        AssetRelationshipTypeEntity entity = new AssetRelationshipTypeEntity(
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
    public Optional<AssetRelationshipType> findById(UUID id) {
        return repository.findById(id).map(entity -> new AssetRelationshipType(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        ));
    }

    @Override
    public List<AssetRelationshipType> findAll() {
        return repository.findAll().stream()
                .map(entity -> new AssetRelationshipType(
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