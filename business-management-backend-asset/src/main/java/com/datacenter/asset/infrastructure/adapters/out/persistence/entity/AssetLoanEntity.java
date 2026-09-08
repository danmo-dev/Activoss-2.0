package com.datacenter.asset.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asset_loans")
public class AssetLoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "asset_id", nullable = false)
    private UUID assetId;

    @Column(name = "origin_company_id", nullable = false)
    private UUID originCompanyId;

    @Column(name = "destination_company_id", nullable = false)
    private UUID destinationCompanyId;

    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private String status;

    private String observation;

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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}