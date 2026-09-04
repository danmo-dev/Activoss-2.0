package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.asset.AssetCode;
import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.exception.AssetNotFoundException;
import com.datacenter.asset.domain.exception.DuplicateAssetCodeException;
import com.datacenter.asset.domain.ports.out.IAssetRepository;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.CreateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.UpdateAssetRequest;
import com.datacenter.asset.infrastructure.adapters.in.rest.dto.response.AssetResponse;
import com.datacenter.asset.infrastructure.adapters.in.rest.mapper.AssetRestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetService {

        private final IAssetRepository repositoryPort;
    private final AssetRestMapper restMapper;

        public AssetService(IAssetRepository repositoryPort, AssetRestMapper restMapper) {
        this.repositoryPort = repositoryPort;
        this.restMapper = restMapper;
    }

    public AssetResponse createAsset(CreateAssetRequest request) {
        Asset domainAsset = restMapper.toDomain(request);
        Asset savedAsset = repositoryPort.save(domainAsset);
        return restMapper.toResponse(savedAsset);
    }

    public List<AssetResponse> findAll() {
        return repositoryPort.findAll().stream()
                .map(restMapper::toResponse)
                .toList();
    }

    public AssetResponse findById(UUID id) {
        return repositoryPort.findById(new AssetId(id))
                .map(restMapper::toResponse)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with id: " + id));
    }

    public AssetResponse findByCode(String code) {
        return repositoryPort.findByCode(AssetCode.of(code))
            .map(restMapper::toResponse)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with code: " + code));
    }

    public AssetResponse updateAsset(
            UUID id,
            UpdateAssetRequest request
    ) {

        Asset existingAsset = repositoryPort.findById(new AssetId(id))
                .orElseThrow(() ->
                        new AssetNotFoundException(
                                "Asset not found with id: " + id
                        )
                );

        if (!existingAsset.getCode().value().equals(request.getCode())
                && repositoryPort.existsByCode(AssetCode.of(request.getCode()))) {

            throw new DuplicateAssetCodeException(
                    "An asset already exists with code: "
                            + request.getCode()
            );
        }
        

        existingAsset = existingAsset.update(request.getCompanyId(), request.getAssetTypeId(), request.getSubAssetTypeId(),
                request.getOwnershipTypeId(), request.getAssetStatusId(), request.getLocationId(), request.getOwnerId(),
                request.getCode(), request.getName(), request.getDescription(), request.getRegistrationDate());

        Asset updatedAsset = repositoryPort.save(existingAsset);

        return restMapper.toResponse(updatedAsset);
    }
}