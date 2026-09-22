package com.datacenter.asset.infrastructure.adapters.in.rest.mappers.asset;

import com.datacenter.asset.domain.models.asset.AssetRelationship;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.asset.AssetRelationshipResponse;
import org.springframework.stereotype.Component;

@Component("assetRelationshipRestMapper")
public class AssetRelationshipMapper {

    public AssetRelationshipResponse toResponse(AssetRelationship domain) {
        if (domain == null) {
            return null;
        }
        
        return new AssetRelationshipResponse(
                domain.getId(),
                domain.getParentAssetId(),
                domain.getChildAssetId(),
                domain.getRelationshipTypeId(),
                domain.getRegistrationDate()
        );
    }
}