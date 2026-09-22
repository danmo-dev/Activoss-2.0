package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.in.asset.ManageRelationshipTypesUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/relationship-types")
public class RelationshipTypeController {

    private final ManageRelationshipTypesUseCase useCase;

    public RelationshipTypeController(ManageRelationshipTypesUseCase useCase) {
        this.useCase = useCase;
    }

    public record CreateRelationshipTypeRequest(String code, String name) {}
    public record UpdateRelationshipTypeRequest(String code, String name) {}

    @PostMapping
    public ResponseEntity<AssetRelationshipType> create(@RequestBody CreateRelationshipTypeRequest request) {
        return ResponseEntity.ok(
                useCase.createAssetRelationshipType(request.code(), request.name())
        );
    }

    @GetMapping
    public ResponseEntity<List<AssetRelationshipType>> findAll() {
        return ResponseEntity.ok(useCase.getAllAssetRelationshipTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetRelationshipType> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetRelationshipType> update(
            @PathVariable UUID id, 
            @RequestBody UpdateRelationshipTypeRequest request) {
        return ResponseEntity.ok(
                useCase.update(id, request.code(), request.name())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}