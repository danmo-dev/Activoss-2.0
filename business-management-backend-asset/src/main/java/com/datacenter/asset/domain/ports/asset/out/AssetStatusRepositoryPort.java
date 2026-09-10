package com.datacenter.asset.domain.ports.asset.out;

import com.datacenter.asset.domain.configuration.AssetStatus;

import java.util.List;

public interface AssetStatusRepositoryPort {

    AssetStatus save(AssetStatus assetStatus);

    List<AssetStatus> findAll();
}
