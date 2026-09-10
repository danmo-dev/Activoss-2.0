package com.datacenter.asset.domain.ports.asset.out;

import com.datacenter.asset.domain.loan.AssetLoan;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetLoanRepositoryPort {
    AssetLoan save(AssetLoan loan);
    Optional<AssetLoan> findById(UUID id);
    List<AssetLoan> findByAssetId(UUID assetId);
    boolean hasActiveLoan(UUID assetId);
}