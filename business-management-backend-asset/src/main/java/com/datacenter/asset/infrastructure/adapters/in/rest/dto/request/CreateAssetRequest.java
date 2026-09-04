package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateAssetRequest {

    @NotNull(message = "Company ID is required")
    private UUID companyId;

    @NotNull(message = "Asset Type ID is required")
    private UUID assetTypeId;

    private UUID subAssetTypeId;

    @NotNull(message = "Ownership Type ID is required")
    private UUID ownershipTypeId;

    @NotNull(message = "Asset Status ID is required")
    private UUID assetStatusId;

    @NotNull(message = "Location ID is required")
    private UUID locationId;

    private UUID ownerId;

    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Registration date is required")
    private LocalDate registrationDate;
}
