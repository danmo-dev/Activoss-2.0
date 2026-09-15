package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetQueryService {

    private final AssetRepositoryPort assetRepositoryPort;

    public List<Asset> searchWithFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        return assetRepositoryPort.findByFilters(typeId, statusId, locationId, keyword);
    }

    public List<Asset> getAssignedAssets(UUID personId) {
        // Regla de negocio: un colaborador solo puede consultar los activos que tiene asignados
        return assetRepositoryPort.findAssignedToPerson(personId);
    }
}
