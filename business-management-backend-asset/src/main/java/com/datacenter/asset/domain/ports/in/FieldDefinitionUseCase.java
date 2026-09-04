package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldDefinitionRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionUseCase {

    FieldDefinitionResponse create(CreateFieldDefinitionRequest request);

    List<FieldDefinitionResponse> findAll();

    FieldDefinitionResponse findById(UUID id);

    List<FieldDefinitionResponse> findBySubAssetTypeId(UUID subAssetTypeId);

    List<FieldDefinitionResponse> findByFieldGroupId(UUID fieldGroupId);

    FieldDefinitionResponse update(UUID id, UpdateFieldDefinitionRequest request);

    void activate(UUID id);

    void deactivate(UUID id);
}