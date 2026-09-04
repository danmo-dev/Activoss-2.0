package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldGroupRepositoryPort {

    FieldGroup save(FieldGroup fieldGroup);

    List<FieldGroup> findAll();

    Optional<FieldGroup> findById(UUID id);

    List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId);

    FieldGroup update(FieldGroup fieldGroup);
}