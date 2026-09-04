package com.datacenter.asset.domain.exception;

public class DuplicateAssetCodeException extends RuntimeException {
    public DuplicateAssetCodeException(String message) { super(message); }
}