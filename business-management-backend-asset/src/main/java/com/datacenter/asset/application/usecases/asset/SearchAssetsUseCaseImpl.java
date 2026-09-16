package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.ports.in.asset.SearchAssetsUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchAssetsUseCaseImpl implements SearchAssetsUseCase {

    private final AssetRepositoryPort assetRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<Asset> searchWithFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        return assetRepositoryPort.findByFilters(typeId, statusId, locationId, keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> getMyAssignedAssets(UUID personId) {
        // Regla de negocio: un colaborador solo puede consultar los activos que tiene asignados
        return assetRepositoryPort.findAssignedToPerson(personId);
    }
}