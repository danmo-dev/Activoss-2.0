package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.configuration.AssetType;
import com.datacenter.asset.domain.ports.asset.out.AssetTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetTypeEntity;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper.AssetTypePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetTypeJpaRepository;

import org.springframework.stereotype.Component;
 
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
@SuppressWarnings("null")
@Component
public class AssetTypeRepositoryAdapter
        implements AssetTypeRepositoryPort {
 
    private final AssetTypeJpaRepository repository;
    private final AssetTypePersistenceMapper mapper;
 
    public AssetTypeRepositoryAdapter(
            AssetTypeJpaRepository repository,
            AssetTypePersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }
 
    @Override
    public AssetType save(AssetType assetType) {
 
        AssetTypeEntity entity =
                mapper.toEntity(assetType);
 
        AssetTypeEntity saved =
                repository.save(entity);
 
        return mapper.toDomain(saved);
    }
 
    @Override
    public List<AssetType> findAll() {
 
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
 
    @Override
    public Optional<AssetType> findById(UUID id) {
 
        return repository.findById(id)
                .map(mapper::toDomain);
    }
 
    @Override
    public Optional<AssetType> findByCode(String code) {
 
        return repository.findByCode(code)
                .map(mapper::toDomain);
    }
 
    @Override
    public boolean existsByCode(String code) {
 
        return repository.existsByCode(code);
    }
}