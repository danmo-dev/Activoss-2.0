package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.configuration.AssetRelationshipType;
import com.datacenter.asset.domain.ports.asset.in.ManageRelationshipTypesUseCase;
import com.datacenter.asset.domain.ports.asset.out.RelationshipTypeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipTypeService
        implements ManageRelationshipTypesUseCase {

    private final RelationshipTypeRepositoryPort repositoryPort;

    public RelationshipTypeService(
            RelationshipTypeRepositoryPort repositoryPort
    ) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetRelationshipType createAssetRelationshipType(
            String code,
            String name
    ) {

        AssetRelationshipType relationshipType =
                new AssetRelationshipType(
                        null,
                        code,
                        name
                );

        return repositoryPort.save(relationshipType);
    }

    @Override
    public List<AssetRelationshipType>
    getAllAssetRelationshipTypes() {

        return repositoryPort.findAll();
    }
}