package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.models.loan.AssetLoan;
import com.datacenter.asset.domain.models.loan.LoanStatus;
import com.datacenter.asset.domain.ports.out.asset.AssetLoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetLoanService {

    private final AssetLoanRepositoryPort loanRepository;

    public AssetLoan create(AssetLoan loan) {
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

    public AssetLoan registerReturn(UUID loanId, String returnObservation) {
        AssetLoan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        loan.returnAsset(returnObservation);
        return loanRepository.save(loan);
    }

    public List<AssetLoan> findByAsset(UUID assetId) {
        return loanRepository.findByAssetId(assetId);
    }
}
