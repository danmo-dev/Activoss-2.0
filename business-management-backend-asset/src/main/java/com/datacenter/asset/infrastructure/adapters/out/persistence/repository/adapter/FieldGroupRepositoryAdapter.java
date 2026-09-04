package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.out.FieldGroupRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.FieldGroupEntity;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.FieldGroupPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.FieldGroupJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FieldGroupRepositoryAdapter implements FieldGroupRepositoryPort {

    private final FieldGroupJpaRepository repository;
    private final FieldGroupPersistenceMapper mapper;

    public FieldGroupRepositoryAdapter(
            FieldGroupJpaRepository repository,
            FieldGroupPersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FieldGroup save(FieldGroup fieldGroup) {
        FieldGroupEntity entity = mapper.toEntity(fieldGroup);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<FieldGroup> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<FieldGroup> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public FieldGroup update(FieldGroup fieldGroup) {
        FieldGroupEntity entity = mapper.toEntity(fieldGroup);
        return mapper.toDomain(repository.save(entity));
    }
}