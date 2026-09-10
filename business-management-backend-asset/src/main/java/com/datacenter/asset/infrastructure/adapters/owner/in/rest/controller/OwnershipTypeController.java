package com.datacenter.asset.infrastructure.adapters.owner.in.rest.controller;

import com.datacenter.asset.domain.owner.OwnershipType;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnershipTypesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ownership-types")
public class OwnershipTypeController {

    private final ManageOwnershipTypesUseCase useCase;

    public OwnershipTypeController(
            ManageOwnershipTypesUseCase useCase
    ) {
        this.useCase = useCase;
    }

    public record CreateOwnershipTypeRequest(
            String code,
            String name
    ) {}

    @PostMapping
    public ResponseEntity<OwnershipType> create(
            @RequestBody CreateOwnershipTypeRequest request
    ) {
        return ResponseEntity.ok(
                useCase.createOwnershipType(
                        request.code(),
                        request.name()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<OwnershipType>> findAll() {
        return ResponseEntity.ok(
                useCase.getAllOwnershipTypes()
        );
    }
}
