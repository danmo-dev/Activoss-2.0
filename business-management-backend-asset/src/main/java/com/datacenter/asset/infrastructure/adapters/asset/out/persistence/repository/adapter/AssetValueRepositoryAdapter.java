package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.asset.AssetValue;
import com.datacenter.asset.domain.ports.asset.out.AssetValueRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper.AssetValuePersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetValueJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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