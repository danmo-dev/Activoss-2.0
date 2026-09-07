package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.asset.AssetHistory;
import com.datacenter.asset.domain.ports.out.AssetHistoryRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.persistence.mapper.AssetHistoryPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.AssetHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AssetHistoryRepositoryAdapter implements AssetHistoryRepositoryPort {

    private final AssetHistoryJpaRepository jpaRepository;
    private final AssetHistoryPersistenceMapper mapper;

    @Override
    public AssetHistory save(AssetHistory history) {
        var entity = jpaRepository.save(mapper.toEntity(history));
        return mapper.toDomain(entity);
    }

    @Override
    public List<AssetHistory> findByAssetId(UUID assetId) {
        return jpaRepository.findByAssetIdOrderByEventDateDesc(assetId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}