package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetAssignmentPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetAssignmentJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
@SuppressWarnings("null")
@Repository
@RequiredArgsConstructor
public class AssetAssignmentRepositoryAdapter implements AssetAssignmentRepositoryPort {

    private final AssetAssignmentJpaRepository jpaRepository;
    private final AssetAssignmentPersistenceMapper mapper;

    @Override
    public AssetAssignment save(AssetAssignment assignment) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(assignment)));
    }

    @Override
    public Optional<AssetAssignment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean hasActiveAssignment(UUID assetId) {
        return jpaRepository.existsByAssetIdAndIsActiveTrue(assetId);
    }
}