package com.datacenter.asset.domain.ports.fieldDefinition.out;

import com.datacenter.asset.domain.fielddefinition.FieldDefinition;

import jakarta.persistence.Table;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Table(name = "field_definitions")
public interface FieldDefinitionRepositoryPort {

    FieldDefinition save(FieldDefinition fieldDefinition);

    List<FieldDefinition> findAll();

    Optional<FieldDefinition> findById(UUID id);

    List<FieldDefinition> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinition> findByFieldGroupId(UUID fieldGroupId);

    FieldDefinition update(FieldDefinition fieldDefinition);
}