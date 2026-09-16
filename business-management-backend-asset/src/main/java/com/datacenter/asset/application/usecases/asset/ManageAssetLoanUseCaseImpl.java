package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.loan.AssetLoan;
import com.datacenter.asset.domain.models.loan.LoanStatus;
import com.datacenter.asset.domain.ports.in.asset.ManageAssetLoanUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetLoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageAssetLoanUseCaseImpl implements ManageAssetLoanUseCase {

    private final AssetLoanRepositoryPort loanRepository;

    @Override
    @Transactional
    public AssetLoan createLoan(AssetLoan loan) {
        if (loan.getOriginCompanyId().equals(loan.getDestinationCompanyId())) {
            throw new BusinessException("La empresa origen y destino no pueden ser la misma.");
        }
        if (loanRepository.hasActiveLoan(loan.getAssetId())) {
            throw new BusinessException("El activo ya tiene un préstamo activo interempresa.");
        }
        loan.setLoanDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);
        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public AssetLoan registerReturn(UUID loanId, String returnObservation) {
        AssetLoan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));
        loan.returnAsset(returnObservation);
        return loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetLoan> getLoansByAsset(UUID assetId) {
        return loanRepository.findByAssetId(assetId);
    }
}