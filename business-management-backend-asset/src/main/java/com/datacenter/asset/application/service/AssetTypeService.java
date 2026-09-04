package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.configuration.AssetType;
import com.datacenter.asset.domain.ports.out.AssetTypeRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateAssetTypeRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetTypeResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.AssetTypeRestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetTypeService {

    private final AssetTypeRepositoryPort repositoryPort;

    private final AssetTypeRestMapper mapper;

    public AssetTypeService(
            AssetTypeRepositoryPort repositoryPort,
            AssetTypeRestMapper mapper
    ) {
        this.repositoryPort = repositoryPort;
        this.mapper = mapper;
    }

    public AssetTypeResponse create(
            CreateAssetTypeRequest request
    ) {

        if (repositoryPort.existsByCode(request.getCode())) {
            throw new RuntimeException(
                    "Asset type already exists with code: "
                            + request.getCode()
            );
        }

        AssetType assetType =
                mapper.toDomain(request);

        AssetType saved =
                repositoryPort.save(assetType);

        return mapper.toResponse(saved);
    }

    public List<AssetTypeResponse> findAll() {

        return repositoryPort.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AssetTypeResponse findById(UUID id) {

        return repositoryPort.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Asset type not found with id: "
                                        + id
                        )
                );
    }

    public AssetTypeResponse update(
            UUID id,
            UpdateAssetTypeRequest request
    ) {

        AssetType existing =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Asset type not found with id: "
                                                + id
                                )
                        );

        if (!existing.getCode().equals(request.getCode())
                && repositoryPort.existsByCode(request.getCode())) {

            throw new RuntimeException(
                    "Asset type already exists with code: "
                            + request.getCode()
            );
        }

        existing.setCode(request.getCode());
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        AssetType updated =
                repositoryPort.save(existing);

        return mapper.toResponse(updated);
    }

    public AssetTypeResponse activate(UUID id) {

        AssetType assetType =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Asset type not found with id: "
                                                + id
                                )
                        );

        assetType.setActive(true);

        return mapper.toResponse(
                repositoryPort.save(assetType)
        );
    }

        public AssetTypeResponse deactivate(UUID id) {

        AssetType assetType =
                repositoryPort.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Asset type not found with id: "
                                                + id
                                )
                        );

        assetType.setActive(false);

        return mapper.toResponse(
                repositoryPort.save(assetType)
        );
    }
}