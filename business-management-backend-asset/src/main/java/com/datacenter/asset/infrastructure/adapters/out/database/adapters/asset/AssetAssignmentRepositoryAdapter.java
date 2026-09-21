package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentState;
import com.datacenter.asset.domain.ports.out.asset.AssetAssignmentRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetAssignmentEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetAssignmentPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetAssignmentJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssetAssignmentRepositoryAdapter implements AssetAssignmentRepositoryPort {

    private final AssetAssignmentJpaRepository jpaRepository;
    private final AssetAssignmentPersistenceMapper mapper;

    @Override
    public AssetAssignment save(AssetAssignment assignment) {
        AssetAssignmentEntity entity = mapper.toEntity(assignment);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<AssetAssignment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean hasActiveAssignment(UUID assetId) {
        return jpaRepository.existsByAssetIdAndIsActiveTrue(assetId);
    }

    @Override
    public List<AssetAssignment> findByState(AssignmentState state) {
        return jpaRepository.findByState(state.name())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AssetAssignment> findByPersonIdAndIsActiveTrue(UUID personId) {
        return jpaRepository.findByPersonIdAndIsActiveTrue(personId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AssetAssignment> findByNotesStartingWith(String prefix) {
        return jpaRepository.findByNotesStartingWith(prefix)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}