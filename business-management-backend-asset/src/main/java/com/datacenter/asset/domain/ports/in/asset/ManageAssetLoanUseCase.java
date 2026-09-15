package com.datacenter.asset.domain.ports.in.asset;

import com.datacenter.asset.domain.models.loan.AssetLoan;
import java.util.List;
import java.util.UUID;

public interface ManageAssetLoanUseCase {
    AssetLoan createLoan(AssetLoan loan);
    AssetLoan registerReturn(UUID loanId, String returnObservation);
    List<AssetLoan> getLoansByAsset(UUID assetId);
}