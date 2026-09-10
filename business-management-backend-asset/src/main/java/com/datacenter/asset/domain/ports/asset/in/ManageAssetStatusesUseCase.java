package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.configuration.AssetStatus;

import java.util.List;

public interface ManageAssetStatusesUseCase {

    AssetStatus createAssetStatus(String code, String name);

    List<AssetStatus> getAllAssetStatuses();
}