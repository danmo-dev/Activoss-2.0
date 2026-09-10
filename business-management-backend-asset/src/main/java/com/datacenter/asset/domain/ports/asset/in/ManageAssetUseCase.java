package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.asset.Asset;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ManageAssetUseCase {
    Asset createAsset(Asset asset);
    List<Asset> findAll();
    Asset findById(UUID id);
    Asset findByCode(String code);
    Asset updateAsset(UUID id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                      UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                      String code, String name, String description, LocalDate registrationDate);
}
