package com.datacenter.asset.domain.ports.fieldDefinition.in;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionUseCase {

    FieldDefinition create(FieldDefinition fieldDefinition);

    List<FieldDefinition> findAll();

    FieldDefinition findById(UUID id);

    List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId);

    FieldDefinition update(FieldDefinition fieldDefinition);

    void activate(UUID id);

    void deactivate(UUID id);
}