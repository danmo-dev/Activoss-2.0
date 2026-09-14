package com.datacenter.asset.infrastructure.adapters.owner.in.rest.controller;

import com.datacenter.asset.domain.owner.OwnershipType;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnershipTypesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ownership-types")
public class OwnershipTypeController {

    private final ManageOwnershipTypesUseCase useCase;

    public OwnershipTypeController(ManageOwnershipTypesUseCase useCase) {
        this.useCase = useCase;
    }

    public record CreateOwnershipTypeRequest(String code, String name) {}
    public record UpdateOwnershipTypeRequest(String code, String name) {}

    @PostMapping
    public ResponseEntity<OwnershipType> create(@RequestBody CreateOwnershipTypeRequest request) {
        return ResponseEntity.ok(
                useCase.createOwnershipType(request.code(), request.name())
        );
    }

    @GetMapping
    public ResponseEntity<List<OwnershipType>> findAll() {
        return ResponseEntity.ok(useCase.getAllOwnershipTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnershipType> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnershipType> update(
            @PathVariable UUID id, 
            @RequestBody UpdateOwnershipTypeRequest request) {
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