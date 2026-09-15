package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetValuePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetValueJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        var entities = values.stream().map(mapper::toEntity).collect(Collectors.toList());
        return jpaRepository.saveAll(entities).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<AssetValue> findByAssetId(UUID assetId) {
        return jpaRepository.findByAssetId(assetId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}