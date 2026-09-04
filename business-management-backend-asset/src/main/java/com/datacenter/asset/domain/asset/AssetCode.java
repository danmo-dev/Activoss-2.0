package com.datacenter.asset.domain.asset;

import com.datacenter.asset.domain.exception.InvalidAssetException;

public record AssetCode(String value) {
    public AssetCode {
        if (value == null || value.isBlank() || value.length() > 50) {
            throw new InvalidAssetException("Asset code must contain between 1 and 50 characters");
        }
        value = value.trim();
    }

    public static AssetCode of(String value) {
        return new AssetCode(value);
    }
}