package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetRelationshipsUseCase;
import com.datacenter.asset.domain.ports.asset.out.AssetRelationshipRepositoryPort;
import com.datacenter.asset.domain.ports.asset.out.AssetRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetRelationshipService implements ManageAssetRelationshipsUseCase {

    private final AssetRelationshipRepositoryPort relationshipRepository;
    private final AssetRepositoryPort assetRepository;

    @Override
    @Transactional
    public AssetRelationship createRelationship(AssetRelationship relationship) {
    if (assetRepository.findById(new AssetId(relationship.getParentAssetId())).isEmpty() ||
        assetRepository.findById(new AssetId(relationship.getChildAssetId())).isEmpty()) {
        throw new IllegalArgumentException("El activo padre o hijo no existe en el inventario.");
    }
    relationship.setRegistrationDate(LocalDateTime.now());
    return relationshipRepository.save(relationship);
    }

    @Override
    public List<AssetRelationship> findChildrenByParentId(UUID parentAssetId) {
        return relationshipRepository.findByParentAssetId(parentAssetId);
    }
}
