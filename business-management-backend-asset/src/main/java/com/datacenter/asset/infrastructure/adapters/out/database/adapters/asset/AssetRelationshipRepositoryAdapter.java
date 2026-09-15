package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.out.asset.AssetRelationshipRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetRelationshipEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetRelationshipMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetRelationshipJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("null")
@Repository
@RequiredArgsConstructor
public class AssetRelationshipRepositoryAdapter implements AssetRelationshipRepositoryPort {

    private final AssetRelationshipJpaRepository jpaRepository;
    private final AssetRelationshipMapper mapper;

    @Override
    public AssetRelationship save(AssetRelationship relationship) {
        AssetRelationshipEntity entity = mapper.toEntity(relationship);
        AssetRelationshipEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<AssetRelationship> findByParentAssetId(UUID parentAssetId) {
        return jpaRepository.findByParentAssetId(parentAssetId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
