package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.ports.out.IAssetRepository;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetEntity;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.AssetPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.AssetJpaRepository;
import org.springframework.stereotype.Component;
 
import java.util.List;
import java.util.Optional;
 
@Component
public class AssetRepositoryImpl implements IAssetRepository {
    private final AssetJpaRepository repository;
    private final AssetPersistenceMapper mapper;
 
    public AssetRepositoryImpl(AssetJpaRepository repository, AssetPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
 
    @Override
    public Asset save(Asset asset) {
        AssetEntity saved = repository.save(mapper.toEntity(asset));
        return mapper.toDomain(saved);
    }
 
    @Override
    public List<Asset> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
 
    @Override
    public Optional<Asset> findById(AssetId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }
 
    @Override
    public Optional<Asset> findByCode(AssetCode code) {
        return repository.findByCode(code.value()).map(mapper::toDomain);
    }
 
    @Override
    public boolean existsByCode(AssetCode code) {
        return repository.existsByCode(code.value());
    }
}