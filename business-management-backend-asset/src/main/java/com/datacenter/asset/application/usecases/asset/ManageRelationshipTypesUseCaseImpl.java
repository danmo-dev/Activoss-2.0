package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.in.asset.ManageRelationshipTypesUseCase;
import com.datacenter.asset.application.service.asset.RelationshipTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageRelationshipTypesUseCaseImpl implements ManageRelationshipTypesUseCase {

    private final RelationshipTypeService relationshipTypeService;

    @Override
    @Transactional
    public AssetRelationshipType createAssetRelationshipType(String code, String name) {
        return relationshipTypeService.create(code, name);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetRelationshipType getById(UUID id) {
        return relationshipTypeService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRelationshipType> getAllAssetRelationshipTypes() {
        return relationshipTypeService.findAll();
    }

    @Override
    @Transactional
    public AssetRelationshipType update(UUID id, String code, String name) {
        return relationshipTypeService.update(id, code, name);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        relationshipTypeService.delete(id);
    }
}
