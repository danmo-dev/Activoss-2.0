package com.datacenter.asset.domain.asset;

import java.util.Objects;
import java.util.UUID;

public record AssetId(UUID value) {
    public AssetId {
        Objects.requireNonNull(value, "Asset id is required");
    }

    public static AssetId generate() {
        return new AssetId(UUID.randomUUID());
    }
}