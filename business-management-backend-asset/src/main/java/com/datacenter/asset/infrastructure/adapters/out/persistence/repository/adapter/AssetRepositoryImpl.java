package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.ports.out.IAssetRepository;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetEntity;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.AssetPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.AssetJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
@Component
public class AssetRepositoryImpl implements IAssetRepository {
    private final AssetJpaRepository repository;
    private final AssetPersistenceMapper mapper;

    // Nuevo: inyección de EntityManager
    private final EntityManager entityManager;

    public AssetRepositoryImpl(AssetJpaRepository repository, AssetPersistenceMapper mapper, EntityManager entityManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Asset save(Asset asset) {
        AssetEntity entity = mapper.toEntity(asset);

        // Nuevo: usar merge para gestionar insert/update automáticamente
        AssetEntity mergedEntity = entityManager.merge(entity);

        return mapper.toDomain(mergedEntity);
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

    // Nuevo: versión con String code (sobrecarga)
    public Optional<Asset> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }

    // Nuevo: versión con Collectors para findAll
    public List<Asset> findAllWithCollectors() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}