package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;

import java.util.List;
import java.util.UUID;

public interface FieldGroupUseCase {

    FieldGroup create(FieldGroup fieldGroup);

    List<FieldGroup> findAll();

    FieldGroup findById(UUID id);

    List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId);

    FieldGroup update(UUID id, String name, Integer displayOrder, UUID subAssetTypeId);

    void activate(UUID id);

    void deactivate(UUID id);
}
