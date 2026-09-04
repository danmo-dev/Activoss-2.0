package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.in.FieldDefinitionUseCase;
import com.datacenter.asset.domain.ports.in.FieldGroupUseCase;
import com.datacenter.asset.domain.ports.out.SubAssetTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateSubAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldDefinitionResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupFormResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.FieldGroupResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.SubAssetTypeFormResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.SubAssetTypeResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.SubAssetTypeRestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubAssetTypeService {

    private final SubAssetTypeRepositoryPort repositoryPort;
    private final SubAssetTypeRestMapper mapper;
    private final FieldGroupUseCase fieldGroupUseCase;
    private final FieldDefinitionUseCase fieldDefinitionUseCase;

    public SubAssetTypeService(
            SubAssetTypeRepositoryPort repositoryPort,
            SubAssetTypeRestMapper mapper,
            FieldGroupUseCase fieldGroupUseCase,
            FieldDefinitionUseCase fieldDefinitionUseCase
    ) {
        this.repositoryPort = repositoryPort;
        this.mapper = mapper;
        this.fieldGroupUseCase = fieldGroupUseCase;
        this.fieldDefinitionUseCase = fieldDefinitionUseCase;
    }

    public SubAssetTypeResponse create(
            CreateSubAssetTypeRequest request
    ) {

        if (repositoryPort.existsByCode(request.getCode())) {
            throw new RuntimeException(
                    "Sub asset type already exists with code: "
                            + request.getCode()
            );
        }

        SubAssetType subAssetType =
                mapper.toDomain(request);

        SubAssetType saved =
                repositoryPort.save(subAssetType);

        return mapper.toResponse(saved);
    }

    public List<SubAssetTypeResponse> findAll() {

        return repositoryPort.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<SubAssetTypeResponse> findByAssetTypeId(
            UUID assetTypeId
    ) {

        return repositoryPort
                .findByAssetTypeId(assetTypeId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SubAssetTypeResponse findById(UUID id) {

        return repositoryPort.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sub asset type not found with id: "
                                        + id
                        )
                );
    }

    public SubAssetTypeFormResponse getFormBySubAssetTypeId(UUID subAssetTypeId) {
        SubAssetTypeResponse subAssetType = findById(subAssetTypeId);

        List<FieldGroupResponse> rawGroups = fieldGroupUseCase.findBySubAssetTypeId(subAssetTypeId);
        List<FieldDefinitionResponse> rawDefinitions = fieldDefinitionUseCase.findBySubAssetTypeId(subAssetTypeId);

        Map<UUID, List<FieldDefinitionResponse>> fieldsByGroupId = rawDefinitions.stream()
        .filter(field -> field.fieldGroupId() != null)
        .collect(Collectors.groupingBy(FieldDefinitionResponse::fieldGroupId));
        
        List<FieldGroupFormResponse> groups = rawGroups.stream()
                .map(group -> new FieldGroupFormResponse(
                        group.getId(),
                        group.getName(),
                        group.getDisplayOrder(),
                        fieldsByGroupId.getOrDefault(group.getId(), List.of())
                ))
                .toList();

        return new SubAssetTypeFormResponse(
                subAssetTypeId,
                subAssetType.getName(),
                groups
        );
    }

    public SubAssetTypeResponse update(
            UUID id,
            UpdateSubAssetTypeRequest request
    ) {

        SubAssetType existing =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sub asset type not found with id: "
                                                + id
                                )
                        );

        if (!existing.getCode().equals(request.getCode())
                && repositoryPort.existsByCode(request.getCode())) {

            throw new RuntimeException(
                    "Sub asset type already exists with code: "
                            + request.getCode()
            );
        }

        existing.setCode(request.getCode());
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        return mapper.toResponse(
                repositoryPort.save(existing)
        );
    }

    public SubAssetTypeResponse activate(UUID id) {

        SubAssetType subAssetType =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sub asset type not found with id: "
                                                + id
                                )
                        );

        subAssetType.setActive(true);

        return mapper.toResponse(
                repositoryPort.save(subAssetType)
        );
    }

    public SubAssetTypeResponse desactivate(UUID id) {

        SubAssetType subAssetType =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sub asset type not found with id: "
                                                + id
                                )
                        );

        subAssetType.setActive(false);

        return mapper.toResponse(
                repositoryPort.save(subAssetType)
        );
    }
}