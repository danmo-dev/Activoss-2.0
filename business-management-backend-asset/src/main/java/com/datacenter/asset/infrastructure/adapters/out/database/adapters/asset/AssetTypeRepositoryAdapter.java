package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;
import com.datacenter.asset.domain.ports.out.asset.AssetTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetTypeEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetTypePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetTypeJpaRepository;

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

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}