package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.asset.in.ManageRelationshipTypesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relationship-types")
public class RelationshipTypeController {

    private final ManageRelationshipTypesUseCase useCase;

    public RelationshipTypeController(
            ManageRelationshipTypesUseCase useCase
    ) {
        this.useCase = useCase;
    }

    public record CreateRelationshipTypeRequest(
            String code,
            String name
    ) {}

    @PostMapping
    public ResponseEntity<AssetRelationshipType> create(
            @RequestBody CreateRelationshipTypeRequest request
    ) {
        return ResponseEntity.ok(
                useCase.createAssetRelationshipType(
                        request.code(),
                        request.name()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AssetRelationshipType>>
    findAll() {

        return ResponseEntity.ok(
                useCase.getAllAssetRelationshipTypes()
        );
    }
}
