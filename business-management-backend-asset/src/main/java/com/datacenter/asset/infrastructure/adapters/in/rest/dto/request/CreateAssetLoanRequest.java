package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import java.util.UUID;

public class CreateAssetLoanRequest {
    private UUID assetId;
    private UUID originCompanyId;
    private UUID destinationCompanyId;
    private String observation;

    // Getters y Setters
    public UUID getAssetId() { return assetId; }
    public void setAssetId(UUID assetId) { this.assetId = assetId; }
    public UUID getOriginCompanyId() { return originCompanyId; }
    public void setOriginCompanyId(UUID originCompanyId) { this.originCompanyId = originCompanyId; }
    public UUID getDestinationCompanyId() { return destinationCompanyId; }
    public void setDestinationCompanyId(UUID destinationCompanyId) { this.destinationCompanyId = destinationCompanyId; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}