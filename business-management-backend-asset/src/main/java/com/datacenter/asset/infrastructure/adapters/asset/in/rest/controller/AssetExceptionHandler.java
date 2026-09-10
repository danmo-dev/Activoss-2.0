package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.exception.AssetNotFoundException;
import com.datacenter.asset.domain.exception.DuplicateAssetCodeException;
import com.datacenter.asset.domain.exception.InvalidAssetException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AssetExceptionHandler {
    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(AssetNotFoundException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({InvalidAssetException.class, DuplicateAssetCodeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleInvalid(RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }
}