package com.datacenter.asset.domain.ports.out.fielddefinition;

import com.datacenter.asset.domain.models.fieldgroup.FieldGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldGroupRepositoryPort {

    FieldGroup save(FieldGroup fieldGroup);

    List<FieldGroup> findAll();

    Optional<FieldGroup> findById(UUID id);

    List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId);

    FieldGroup update(FieldGroup fieldGroup);

    void deleteById(UUID id);
}