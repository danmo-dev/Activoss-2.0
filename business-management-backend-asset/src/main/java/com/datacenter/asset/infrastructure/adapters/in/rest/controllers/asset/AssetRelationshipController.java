package com.datacenter.asset.infrastructure.adapters.in.rest.controllers.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetRelationshipsUseCase;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset.UpdateAssetRelationshipRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset.AssetRelationshipMapper; 
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
    private final AssetRelationshipMapper mapper; // Nuevo mapper para convertir a DTO/Response

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

    // 🔹 Nuevo endpoint: actualizar relación
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody UpdateAssetRelationshipRequest request) {

        String user = request.modifiedBy() != null ? request.modifiedBy() : "AdminUser";

        AssetRelationship updated = useCase.update(
                id,
                request.parentAssetId(),
                request.childAssetId(),
                request.relationshipTypeId(),
                user
        );

        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    // 🔹 Nuevo endpoint: eliminar relación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "AdminUser") String user) {

        useCase.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
