package com.datacenter.asset.domain.ports.out.asset;

import com.datacenter.asset.domain.models.loan.AssetLoan;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetLoanRepositoryPort {
    AssetLoan save(AssetLoan loan);
    Optional<AssetLoan> findById(UUID id);
    List<AssetLoan> findByAssetId(UUID assetId);
    boolean hasActiveLoan(UUID assetId);
}