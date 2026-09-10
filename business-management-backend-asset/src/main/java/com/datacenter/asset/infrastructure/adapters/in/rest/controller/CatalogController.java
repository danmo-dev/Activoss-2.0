package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.configuration.*;
import com.datacenter.asset.domain.asset.Owner;
import com.datacenter.asset.domain.ports.in.ManageCatalogsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalogs")
public class CatalogController {

    private final ManageCatalogsUseCase catalogUseCase;

    public CatalogController(ManageCatalogsUseCase catalogUseCase) {
        this.catalogUseCase = catalogUseCase;
    }

    // DTOs (Usando records de Java 16+ para código limpio y sin boilerplate)
    public record CreateCatalogRequest(String code, String name) {
    }

    public record CreateOwnerRequest(UUID companyId, UUID ownershipTypeId) {
    }

    @PostMapping("/asset-statuses")
    public ResponseEntity<AssetStatus> createStatus(@RequestBody CreateCatalogRequest req) {
        return ResponseEntity.ok(catalogUseCase.createAssetStatus(req.code(), req.name()));
    }

    @GetMapping("/asset-statuses")
    public ResponseEntity<List<AssetStatus>> getStatuses() {
        return ResponseEntity.ok(catalogUseCase.getAllAssetStatuses());
    }

    @PostMapping("/ownership-types")
    public ResponseEntity<OwnershipType> createOwnershipType(@RequestBody CreateCatalogRequest req) {
        return ResponseEntity.ok(catalogUseCase.createOwnershipType(req.code(), req.name()));
    }

    @GetMapping("/ownership-types")
    public ResponseEntity<List<OwnershipType>> getOwnershipTypes() {
        return ResponseEntity.ok(catalogUseCase.getAllOwnershipTypes());
    }

    @PostMapping("/relationship-types")
    public ResponseEntity<AssetRelationshipType> createRelationshipType(@RequestBody CreateCatalogRequest req) {
        return ResponseEntity.ok(catalogUseCase.createAssetRelationshipType(req.code(), req.name()));
    }

    @GetMapping("/relationship-types")
    public ResponseEntity<List<AssetRelationshipType>> getRelationshipTypes() {
        return ResponseEntity.ok(catalogUseCase.getAllAssetRelationshipTypes());
    }

    @PostMapping("/owners")
    public ResponseEntity<Owner> createOwner(@RequestBody CreateOwnerRequest req) {
        return ResponseEntity.ok(catalogUseCase.createOwner(req.companyId(), req.ownershipTypeId()));
    }

    @GetMapping("/owners/{companyId}")
    public ResponseEntity<List<Owner>> getOwnersByCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(catalogUseCase.getOwnersByCompany(companyId));
    }
}