package com.datacenter.asset.infrastructure.adapters.asset.in.rest.controller;

import com.datacenter.asset.domain.loan.AssetLoan;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetLoanUseCase;
import com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request.CreateAssetLoanRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class AssetLoanController {

    private final ManageAssetLoanUseCase loanUseCase;

    public AssetLoanController(ManageAssetLoanUseCase loanUseCase) {
        this.loanUseCase = loanUseCase;
    }

    @PostMapping
    public ResponseEntity<AssetLoan> createLoan(@RequestBody CreateAssetLoanRequest request) {
        AssetLoan loan = new AssetLoan();
        loan.setAssetId(request.getAssetId());
        loan.setOriginCompanyId(request.getOriginCompanyId());
        loan.setDestinationCompanyId(request.getDestinationCompanyId());
        loan.setObservation(request.getObservation());
        
        return ResponseEntity.ok(loanUseCase.createLoan(loan));
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<AssetLoan> returnLoan(@PathVariable UUID loanId, @RequestBody Map<String, String> payload) {
        String observation = payload.getOrDefault("observation", "Devuelto en buen estado");
        return ResponseEntity.ok(loanUseCase.registerReturn(loanId, observation));
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<AssetLoan>> getLoansByAsset(@PathVariable UUID assetId) {
        return ResponseEntity.ok(loanUseCase.getLoansByAsset(assetId));
    }
}