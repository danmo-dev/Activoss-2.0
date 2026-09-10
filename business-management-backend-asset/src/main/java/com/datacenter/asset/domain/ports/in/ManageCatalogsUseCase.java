package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.domain.configuration.OwnershipType;
import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.asset.Owner;

import java.util.List;
import java.util.UUID;

public interface ManageCatalogsUseCase {
    AssetStatus createAssetStatus(String code, String name);
    List<AssetStatus> getAllAssetStatuses();

    OwnershipType createOwnershipType(String code, String name);
    List<OwnershipType> getAllOwnershipTypes();

    AssetRelationshipType createAssetRelationshipType(String code, String name);
    List<AssetRelationshipType> getAllAssetRelationshipTypes();

    Owner createOwner(UUID companyId, UUID ownershipTypeId);
    List<Owner> getOwnersByCompany(UUID companyId);
}