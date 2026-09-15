package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetRelationshipsUseCase;
import com.datacenter.asset.application.service.asset.AssetRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetRelationshipsUseCaseImpl implements ManageAssetRelationshipsUseCase {

    private final AssetRelationshipService assetRelationshipService;

    @Override
    @Transactional
    public AssetRelationship createRelationship(AssetRelationship relationship) {
        return assetRelationshipService.create(relationship);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRelationship> findChildrenByParentId(UUID parentAssetId) {
        return assetRelationshipService.findChildren(parentAssetId);
    }
}
