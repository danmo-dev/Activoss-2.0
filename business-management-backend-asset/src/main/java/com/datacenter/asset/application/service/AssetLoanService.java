package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.loan.AssetLoan;
import com.datacenter.asset.domain.loan.LoanStatus;
import com.datacenter.asset.domain.ports.in.ManageAssetLoanUseCase;
import com.datacenter.asset.domain.ports.out.AssetLoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AssetLoanService implements ManageAssetLoanUseCase {

    private final AssetLoanRepositoryPort loanRepository;

    public AssetLoanService(AssetLoanRepositoryPort loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public AssetLoan createLoan(AssetLoan loan) {
        if (loan.getOriginCompanyId().equals(loan.getDestinationCompanyId())) {
            throw new IllegalArgumentException("La empresa origen y destino no pueden ser la misma.");
        }
        if (loanRepository.hasActiveLoan(loan.getAssetId())) {
            throw new IllegalStateException("El activo ya tiene un préstamo activo interempresa.");
        }
        
        loan.setLoanDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);
        return loanRepository.save(loan);
    }

    @Override
    public AssetLoan registerReturn(UUID loanId, String returnObservation) {
        AssetLoan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        
        loan.returnAsset(returnObservation);
        return loanRepository.save(loan);
    }

    @Override
    public List<AssetLoan> getLoansByAsset(UUID assetId) {
        return loanRepository.findByAssetId(assetId);
    }
}