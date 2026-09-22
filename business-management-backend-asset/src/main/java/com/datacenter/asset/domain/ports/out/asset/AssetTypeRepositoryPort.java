package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.configuration.AssetType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetTypeRepositoryPort {

    AssetType save(AssetType assetType);

    List<AssetType> findAll();

    Optional<AssetType> findById(UUID id);

    Optional<AssetType> findByCode(String code);

    boolean existsByCode(String code);

    void deleteById(UUID id);
}