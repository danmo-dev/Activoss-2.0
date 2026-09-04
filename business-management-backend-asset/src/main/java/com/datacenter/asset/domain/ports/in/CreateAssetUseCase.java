package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.asset.Asset;

public interface CreateAssetUseCase {
    Asset createAsset(Asset asset);
}