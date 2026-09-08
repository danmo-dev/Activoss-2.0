package com.datacenter.asset.domain.loan;

import java.time.LocalDateTime;
import java.util.UUID;

public class AssetLoan {
    private UUID id;
    private UUID assetId;
    private UUID originCompanyId;
    private UUID destinationCompanyId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LoanStatus status;
    private String observation;

    public AssetLoan() {}

    public void returnAsset(String returnObservation) {
        if (this.status == LoanStatus.RETURNED) {
            throw new IllegalStateException("El préstamo ya fue devuelto.");
        }
        this.status = LoanStatus.RETURNED;
        this.returnDate = LocalDateTime.now();
        this.observation = this.observation + " | Devolución: " + returnObservation;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAssetId() { return assetId; }
    public void setAssetId(UUID assetId) { this.assetId = assetId; }
    public UUID getOriginCompanyId() { return originCompanyId; }
    public void setOriginCompanyId(UUID originCompanyId) { this.originCompanyId = originCompanyId; }
    public UUID getDestinationCompanyId() { return destinationCompanyId; }
    public void setDestinationCompanyId(UUID destinationCompanyId) { this.destinationCompanyId = destinationCompanyId; }
    public LocalDateTime getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDateTime loanDate) { this.loanDate = loanDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}