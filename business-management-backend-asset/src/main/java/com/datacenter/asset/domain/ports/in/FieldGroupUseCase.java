package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupResponse;

import java.util.List;
import java.util.UUID;

public interface FieldGroupUseCase {

    FieldGroupResponse create(CreateFieldGroupRequest request);

    List<FieldGroupResponse> findAll();

    FieldGroupResponse findById(UUID id);

    List<FieldGroupResponse> findBySubAssetTypeId(UUID subAssetTypeId);

    FieldGroupResponse update(UUID id, UpdateFieldGroupRequest request);

    void activate(UUID id);

    void deactivate(UUID id);
}
