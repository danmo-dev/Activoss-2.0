package com.datacenter.asset.infrastructure.adapters.owner.in.rest.controller;

import com.datacenter.asset.domain.owner.Owner;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private final ManageOwnersUseCase useCase;

    public OwnerController(ManageOwnersUseCase useCase) {
        this.useCase = useCase;
    }

    public record CreateOwnerRequest(UUID companyId, UUID ownershipTypeId) {}
    public record UpdateOwnerRequest(UUID companyId, UUID ownershipTypeId) {}

    @PostMapping
    public ResponseEntity<Owner> create(@RequestBody CreateOwnerRequest request) {
        return ResponseEntity.ok(
                useCase.createOwner(request.companyId(), request.ownershipTypeId())
        );
    }

    @GetMapping
    public ResponseEntity<List<Owner>> findAll() {
        return ResponseEntity.ok(useCase.getAllOwners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Owner>> findByCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(useCase.getOwnersByCompany(companyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> update(
            @PathVariable UUID id, 
            @RequestBody UpdateOwnerRequest request) {
        return ResponseEntity.ok(
                useCase.update(id, request.companyId(), request.ownershipTypeId())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}