package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import com.datacenter.asset.domain.ports.in.asset.SearchAssetsUseCase;
import com.datacenter.asset.application.service.asset.AssetQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchAssetsUseCaseImpl implements SearchAssetsUseCase {

    private final AssetQueryService assetQueryService;

    @Override
    @Transactional(readOnly = true)
    public List<Asset> searchWithFilters(UUID typeId, UUID statusId, UUID locationId, String keyword) {
        return assetQueryService.searchWithFilters(typeId, statusId, locationId, keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> getMyAssignedAssets(UUID personId) {
        return assetQueryService.getAssignedAssets(personId);
    }
}
