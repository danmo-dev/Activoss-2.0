package com.datacenter.asset.domain.ports.asset.in;

import com.datacenter.asset.domain.loan.AssetLoan;
import java.util.List;
import java.util.UUID;

public interface ManageAssetLoanUseCase {
    AssetLoan createLoan(AssetLoan loan);
    AssetLoan registerReturn(UUID loanId, String returnObservation);
    List<AssetLoan> getLoansByAsset(UUID assetId);
}