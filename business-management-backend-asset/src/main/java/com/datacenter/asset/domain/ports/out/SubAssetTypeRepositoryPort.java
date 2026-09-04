package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.configuration.SubAssetType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubAssetTypeRepositoryPort {

    SubAssetType save(SubAssetType subAssetType);

    List<SubAssetType> findAll();

    List<SubAssetType> findByAssetTypeId(UUID assetTypeId);

    Optional<SubAssetType> findById(UUID id);

    Optional<SubAssetType> findByCode(String code);

    boolean existsByCode(String code);
}