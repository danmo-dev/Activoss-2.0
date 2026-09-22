package com.datacenter.asset.infrastructure.adapters.out.database.adapters.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.models.asset.AssetCode;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetAssignmentEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.entities.asset.AssetEntity;
import com.datacenter.asset.infrastructure.adapters.out.database.mappers.asset.AssetPersistenceMapper;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetAssignmentJpaRepository;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetJpaRepository;
import com.datacenter.asset.infrastructure.adapters.out.database.repositories.asset.AssetSpecification;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Component
public class AssetRepositoryAdapter implements AssetRepositoryPort {

    private final AssetJpaRepository repository;
    private final AssetAssignmentJpaRepository assignmentRepository;
    private final AssetPersistenceMapper mapper;
    private final EntityManager entityManager;

    public AssetRepositoryAdapter(AssetJpaRepository repository, 
                                  AssetAssignmentJpaRepository assignmentRepository, 
                                  AssetPersistenceMapper mapper,
                                  EntityManager entityManager) {
        this.repository = repository;
        this.assignmentRepository = assignmentRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Asset save(Asset asset) {
        AssetEntity entity = mapper.toEntity(asset);
        AssetEntity mergedEntity = entityManager.merge(entity);
        return mapper.toDomain(mergedEntity);
    }

    @Override
    public List<Asset> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
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

    @Override
    public Optional<Asset> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public Optional<Asset> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Asset> findByFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        // Delegamos a la Specification (0% SQL)
        return repository.findAll(AssetSpecification.withDynamicFilters(typeId, statusId, locationId, keyword))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Asset> findAssignedToPerson(UUID personId) {
        // Enfoque sin SQL: Buscamos las asignaciones de la persona
        List<AssetAssignmentEntity> assignments = assignmentRepository.findByPersonIdAndIsActiveTrue(personId);
        
        List<UUID> assetIds = assignments.stream()
                .map(AssetAssignmentEntity::getAssetId)
                .collect(Collectors.toList());
                
        if (assetIds.isEmpty()) {
            return List.of();
        }
        
        // Buscamos los activos correspondientes a esos IDs
        return repository.findAllById(assetIds).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}