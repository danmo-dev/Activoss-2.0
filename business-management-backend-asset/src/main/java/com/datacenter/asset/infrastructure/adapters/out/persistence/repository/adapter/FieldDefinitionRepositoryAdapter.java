package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;
import com.datacenter.asset.domain.ports.out.FieldDefinitionRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldDefinitionEntity;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.FieldDefinitionPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.FieldDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FieldDefinitionRepositoryAdapter implements FieldDefinitionRepositoryPort {

    private final FieldDefinitionJpaRepository repository;
    private final FieldDefinitionPersistenceMapper mapper;

    public FieldDefinitionRepositoryAdapter(
            FieldDefinitionJpaRepository repository,
            FieldDefinitionPersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FieldDefinition save(FieldDefinition fieldDefinition) {
        FieldDefinitionEntity entity = mapper.toEntity(fieldDefinition);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<FieldDefinition> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<FieldDefinition> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId) {
        return repository.findByFieldGroupId(fieldGroupId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public FieldDefinition update(FieldDefinition fieldDefinition) {
        FieldDefinitionEntity entity = mapper.toEntity(fieldDefinition);
        return mapper.toDomain(repository.save(entity));
    }
}