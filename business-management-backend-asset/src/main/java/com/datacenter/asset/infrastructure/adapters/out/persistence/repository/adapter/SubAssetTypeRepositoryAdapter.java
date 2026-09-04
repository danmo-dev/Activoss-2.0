package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.out.SubAssetTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.SubAssetTypeEntity;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.SubAssetTypePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.SubAssetTypeJpaRepository;
import org.springframework.stereotype.Component;
 
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
@Component
public class SubAssetTypeRepositoryAdapter
        implements SubAssetTypeRepositoryPort {
 
    private final SubAssetTypeJpaRepository repository;
    private final SubAssetTypePersistenceMapper mapper;
 
    public SubAssetTypeRepositoryAdapter(
            SubAssetTypeJpaRepository repository,
            SubAssetTypePersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }
 
    @Override
    public SubAssetType save(SubAssetType subAssetType) {
 
        SubAssetTypeEntity entity =
                mapper.toEntity(subAssetType);
 
        SubAssetTypeEntity saved =
                repository.save(entity);
 
        return mapper.toDomain(saved);
    }
 
    @Override
    public List<SubAssetType> findAll() {
 
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
 
    @Override
    public List<SubAssetType> findByAssetTypeId(UUID assetTypeId) {
 
        return repository.findByAssetTypeId(assetTypeId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
 
    @Override
    public Optional<SubAssetType> findById(UUID id) {
 
        return repository.findById(id)
                .map(mapper::toDomain);
    }
 
    @Override
    public Optional<SubAssetType> findByCode(String code) {
 
        return repository.findByCode(code)
                .map(mapper::toDomain);
    }
 
    @Override
    public boolean existsByCode(String code) {
 
        return repository.existsByCode(code);
    }
}