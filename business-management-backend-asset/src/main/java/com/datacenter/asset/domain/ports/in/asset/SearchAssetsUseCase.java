package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.asset.Asset;
import java.util.List;
import java.util.UUID;

public interface SearchAssetsUseCase {
    List<Asset> searchWithFilters(UUID typeId, UUID statusId, UUID locationId, String keyword);
    List<Asset> getMyAssignedAssets(UUID personId);
}