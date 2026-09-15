package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.configuration.AssetStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetStatusRepositoryPort {
    AssetStatus save(AssetStatus assetStatus);
    Optional<AssetStatus> findById(UUID id);
    List<AssetStatus> findAll();
    boolean existsByCode(String code);
    void deleteById(UUID id);
}