package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.domain.configuration.OwnershipType;
import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.asset.Owner;

import java.util.List;
import java.util.UUID;

public interface CatalogRepositoryPort {
    AssetStatus saveStatus(AssetStatus status);
    List<AssetStatus> findAllStatuses();

    OwnershipType saveOwnershipType(OwnershipType type);
    List<OwnershipType> findAllOwnershipTypes();

    AssetRelationshipType saveRelationshipType(AssetRelationshipType type);
    List<AssetRelationshipType> findAllRelationshipTypes();

    Owner saveOwner(Owner owner);
    List<Owner> findOwnersByCompanyId(UUID companyId);
}