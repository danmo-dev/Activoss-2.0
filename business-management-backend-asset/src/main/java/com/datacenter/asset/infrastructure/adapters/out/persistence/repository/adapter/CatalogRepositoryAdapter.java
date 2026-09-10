package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.adapter;

import com.datacenter.asset.domain.configuration.*;
import com.datacenter.asset.domain.asset.Owner;
import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.*;
import com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa.*;
import com.datacenter.asset.domain.ports.out.CatalogRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CatalogRepositoryAdapter implements CatalogRepositoryPort {

    private final AssetStatusJpaRepository statusRepo;
    private final OwnershipTypeJpaRepository ownershipRepo;
    private final AssetRelationshipTypeJpaRepository relationshipRepo;
    private final OwnerJpaRepository ownerRepo;

    public CatalogRepositoryAdapter(AssetStatusJpaRepository statusRepo, OwnershipTypeJpaRepository ownershipRepo, AssetRelationshipTypeJpaRepository relationshipRepo, OwnerJpaRepository ownerRepo) {
        this.statusRepo = statusRepo;
        this.ownershipRepo = ownershipRepo;
        this.relationshipRepo = relationshipRepo;
        this.ownerRepo = ownerRepo;
    }

    @Override
    public AssetStatus saveStatus(AssetStatus status) {
        var entity = statusRepo.save(new AssetStatusEntity(status.getId(), status.getCode(), status.getName()));
        return new AssetStatus(entity.getId(), entity.getCode(), entity.getName());
    }

    @Override
    public List<AssetStatus> findAllStatuses() {
        return statusRepo.findAll().stream()
                .map(e -> new AssetStatus(e.getId(), e.getCode(), e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public OwnershipType saveOwnershipType(OwnershipType type) {
        var entity = ownershipRepo.save(new OwnershipTypeEntity(type.getId(), type.getCode(), type.getName()));
        return new OwnershipType(entity.getId(), entity.getCode(), entity.getName());
    }

    @Override
    public List<OwnershipType> findAllOwnershipTypes() {
        return ownershipRepo.findAll().stream()
                .map(e -> new OwnershipType(e.getId(), e.getCode(), e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public AssetRelationshipType saveRelationshipType(AssetRelationshipType type) {
        var entity = relationshipRepo.save(new AssetRelationshipTypeEntity(type.getId(), type.getCode(), type.getName()));
        return new AssetRelationshipType(entity.getId(), entity.getCode(), entity.getName());
    }

    @Override
    public List<AssetRelationshipType> findAllRelationshipTypes() {
        return relationshipRepo.findAll().stream()
                .map(e -> new AssetRelationshipType(e.getId(), e.getCode(), e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Owner saveOwner(Owner owner) {
        var entity = ownerRepo.save(new OwnerEntity(owner.getId(), owner.getCompanyId(), owner.getOwnershipTypeId()));
        return new Owner(entity.getId(), entity.getCompanyId(), entity.getOwnershipTypeId());
    }

    @Override
    public List<Owner> findOwnersByCompanyId(UUID companyId) {
        return ownerRepo.findByCompanyId(companyId).stream()
                .map(e -> new Owner(e.getId(), e.getCompanyId(), e.getOwnershipTypeId()))
                .collect(Collectors.toList());
    }
}