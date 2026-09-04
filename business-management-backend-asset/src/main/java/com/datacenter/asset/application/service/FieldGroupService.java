package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.in.FieldGroupUseCase;
import com.datacenter.asset.domain.ports.out.FieldGroupRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateFieldGroupRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.FieldGroupRestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldGroupService implements FieldGroupUseCase {

    private final FieldGroupRepositoryPort repository;
    private final FieldGroupRestMapper mapper;

    public FieldGroupService(
            FieldGroupRepositoryPort repository,
            FieldGroupRestMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FieldGroupResponse create(CreateFieldGroupRequest request) {

        FieldGroup fieldGroup = new FieldGroup();

        fieldGroup.setSubAssetTypeId(request.getSubAssetTypeId());
        fieldGroup.setName(request.getName());
        fieldGroup.setDescription(request.getDescription());
        fieldGroup.setDisplayOrder(request.getDisplayOrder());
        fieldGroup.setActive(true);

        return mapper.toResponse(repository.save(fieldGroup));
    }

    @Override
    public List<FieldGroupResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FieldGroupResponse findById(UUID id) {

        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field group not found: " + id));

        return mapper.toResponse(fieldGroup);
    }

    @Override
    public List<FieldGroupResponse> findBySubAssetTypeId(UUID subAssetTypeId) {

        return repository.findBySubAssetTypeId(subAssetTypeId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FieldGroupResponse update(
            UUID id,
            UpdateFieldGroupRequest request
    ) {

        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field group not found: " + id));

        fieldGroup.setName(request.getName());
        fieldGroup.setDescription(request.getDescription());
        fieldGroup.setDisplayOrder(request.getDisplayOrder());

        return mapper.toResponse(repository.update(fieldGroup));
    }

    @Override
    public void activate(UUID id) {

        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field group not found: " + id));

        fieldGroup.setActive(true);

        repository.update(fieldGroup);
    }

    @Override
    public void deactivate(UUID id) {

        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Field group not found: " + id));

        fieldGroup.setActive(false);

        repository.update(fieldGroup);
    }
}