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

    public OwnerController(
            ManageOwnersUseCase useCase
    ) {
        this.useCase = useCase;
    }

    public record CreateOwnerRequest(
            UUID companyId,
            UUID ownershipTypeId
    ) {}

    @PostMapping
    public ResponseEntity<Owner> create(
            @RequestBody CreateOwnerRequest request
    ) {
        return ResponseEntity.ok(
                useCase.createOwner(
                        request.companyId(),
                        request.ownershipTypeId()
                )
        );
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Owner>> findByCompany(
            @PathVariable UUID companyId
    ) {
        return ResponseEntity.ok(
                useCase.getOwnersByCompany(companyId)
        );
    }
}