package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.out.asset.AssetRelationshipRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetRelationshipService {

    private final AssetRelationshipRepositoryPort relationshipRepository;
    private final AssetRepositoryPort assetRepository;

    public AssetRelationship create(AssetRelationship relationship) {
        if (assetRepository.findById(new AssetId(relationship.getParentAssetId())).isEmpty() ||
            assetRepository.findById(new AssetId(relationship.getChildAssetId())).isEmpty()) {
            throw new IllegalArgumentException("El activo padre o hijo no existe en el inventario.");
        }
        relationship.setRegistrationDate(LocalDateTime.now());
        return relationshipRepository.save(relationship);
    }

    public List<AssetRelationship> findChildren(UUID parentAssetId) {
        return relationshipRepository.findByParentAssetId(parentAssetId);
    }
}
