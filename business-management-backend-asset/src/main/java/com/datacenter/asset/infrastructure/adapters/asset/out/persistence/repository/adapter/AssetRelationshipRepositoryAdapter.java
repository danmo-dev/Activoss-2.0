package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.asset.out.AssetRelationshipRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetRelationshipEntity;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper.AssetRelationshipMapper;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetRelationshipJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
