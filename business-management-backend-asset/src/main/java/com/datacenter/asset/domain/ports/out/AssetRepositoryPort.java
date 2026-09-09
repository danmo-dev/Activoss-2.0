package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRepositoryPort {
    Asset save(Asset asset);
    List<Asset> findAll();
    Optional<Asset> findById(AssetId id);
    Optional<Asset> findByCode(AssetCode code);
    boolean existsByCode(AssetCode code);
    
    Optional<Asset> findById(UUID id);
    Optional<Asset> findByCode(String code);
    
    List<Asset> findByFilters(UUID typeId, UUID statusId, UUID locationId, String keyword);
    List<Asset> findAssignedToPerson(UUID personId);
}