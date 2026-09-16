package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetRelationshipsUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetRelationshipRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetRelationshipsUseCaseImpl implements ManageAssetRelationshipsUseCase {

    private final AssetRelationshipRepositoryPort relationshipRepository;
    private final AssetRepositoryPort assetRepository;

    @Override
    @Transactional
    public AssetRelationship createRelationship(AssetRelationship relationship) {
        if (assetRepository.findById(new AssetId(relationship.getParentAssetId())).isEmpty() ||
            assetRepository.findById(new AssetId(relationship.getChildAssetId())).isEmpty()) {
            throw new BusinessException("El activo padre o hijo no existe en el inventario.");
        }
        relationship.setRegistrationDate(LocalDateTime.now());
        return relationshipRepository.save(relationship);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRelationship> findChildrenByParentId(UUID parentAssetId) {
        return relationshipRepository.findByParentAssetId(parentAssetId);
    }
}