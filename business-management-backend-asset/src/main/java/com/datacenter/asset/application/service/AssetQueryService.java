package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.asset.Asset;
import com.datacenter.asset.domain.ports.in.SearchAssetsUseCase;
import com.datacenter.asset.domain.ports.out.AssetRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetQueryService implements SearchAssetsUseCase {

    private final AssetRepositoryPort assetRepositoryPort;

    public AssetQueryService(AssetRepositoryPort assetRepositoryPort) {
        this.assetRepositoryPort = assetRepositoryPort;
    }

    @Override
    public List<Asset> searchWithFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        return assetRepositoryPort.findByFilters(typeId, statusId, locationId, keyword);
    }

    @Override
    public List<Asset> getMyAssignedAssets(UUID personId) {
        // Regla de negocio: Un colaborador solo puede consultar los activos que tiene asignados[cite: 2]
        return assetRepositoryPort.findAssignedToPerson(personId);
    }
}