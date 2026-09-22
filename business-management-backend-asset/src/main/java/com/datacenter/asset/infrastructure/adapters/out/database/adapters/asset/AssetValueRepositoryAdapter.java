package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetValuePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetValueJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Repository
@RequiredArgsConstructor
public class AssetValueRepositoryAdapter implements AssetValueRepositoryPort {

    private final AssetValueJpaRepository jpaRepository;
    private final AssetValuePersistenceMapper mapper;

    @Override
    public List<AssetValue> saveAll(List<AssetValue> values) {
        var entities = values.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        return jpaRepository.saveAll(entities)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public AssetValue save(AssetValue value) {
        var entity = mapper.toEntity(value);

        return mapper.toDomain(
                jpaRepository.save(entity)
        );
    }

    @Override
    public List<AssetValue> findByAssetId(UUID assetId) {
        return jpaRepository.findByAssetId(assetId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AssetValue> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}