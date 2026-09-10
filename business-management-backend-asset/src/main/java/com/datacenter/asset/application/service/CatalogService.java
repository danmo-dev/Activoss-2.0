package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.domain.configuration.OwnershipType;
import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.asset.Owner;
import com.datacenter.asset.domain.ports.in.ManageCatalogsUseCase;
import com.datacenter.asset.domain.ports.out.CatalogRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogService implements ManageCatalogsUseCase {

    private final CatalogRepositoryPort repositoryPort;

    public CatalogService(CatalogRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetStatus createAssetStatus(String code, String name) {
        return repositoryPort.saveStatus(new AssetStatus(null, code, name));
    }

    @Override
    public List<AssetStatus> getAllAssetStatuses() {
        return repositoryPort.findAllStatuses();
    }

    @Override
    public OwnershipType createOwnershipType(String code, String name) {
        return repositoryPort.saveOwnershipType(new OwnershipType(null, code, name));
    }

    @Override
    public List<OwnershipType> getAllOwnershipTypes() {
        return repositoryPort.findAllOwnershipTypes();
    }

    @Override
    public AssetRelationshipType createAssetRelationshipType(String code, String name) {
        return repositoryPort.saveRelationshipType(new AssetRelationshipType(null, code, name));
    }

    @Override
    public List<AssetRelationshipType> getAllAssetRelationshipTypes() {
        return repositoryPort.findAllRelationshipTypes();
    }

    @Override
    public Owner createOwner(UUID companyId, UUID ownershipTypeId) {
        return repositoryPort.saveOwner(new Owner(null, companyId, ownershipTypeId));
    }

    @Override
    public List<Owner> getOwnersByCompany(UUID companyId) {
        return repositoryPort.findOwnersByCompanyId(companyId);
    }
}