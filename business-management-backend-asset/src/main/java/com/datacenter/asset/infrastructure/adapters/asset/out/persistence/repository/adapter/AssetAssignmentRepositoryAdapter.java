package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.asset.out.AssetAssignmentRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.mapper.AssetAssignmentPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetAssignmentJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

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