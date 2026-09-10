package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetRelationshipsUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/asset-relationships")
@RequiredArgsConstructor
public class AssetRelationshipController {

    private final ManageAssetRelationshipsUseCase useCase;

    @PostMapping
    public ResponseEntity<AssetRelationship> createRelationship(@RequestBody AssetRelationship relationship) {
        AssetRelationship created = useCase.createRelationship(relationship);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/parent/{parentAssetId}")
    public ResponseEntity<List<AssetRelationship>> getChildren(@PathVariable UUID parentAssetId) {
        List<AssetRelationship> children = useCase.findChildrenByParentId(parentAssetId);
        return ResponseEntity.ok(children);
    }
}