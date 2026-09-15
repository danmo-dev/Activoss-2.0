package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.loan.AssetLoan;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetLoanUseCase;
import com.datacenter.asset.application.service.asset.AssetLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetLoanUseCaseImpl implements ManageAssetLoanUseCase {

    private final AssetLoanService assetLoanService;

    @Override
    @Transactional
    public AssetLoan createLoan(AssetLoan loan) {
        return assetLoanService.create(loan);
    }

    @Override
    @Transactional
    public AssetLoan registerReturn(UUID loanId, String returnObservation) {
        return assetLoanService.registerReturn(loanId, returnObservation);
    }

    @Override
    public List<AssetLoan> getLoansByAsset(UUID assetId) {
        return assetLoanService.findByAsset(assetId);
    }
}