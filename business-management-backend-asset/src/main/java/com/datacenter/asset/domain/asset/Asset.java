package com.datacenter.asset.domain.asset;

import com.datacenter.asset.domain.exception.InvalidAssetException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Asset {
    private final AssetId id;
    private final UUID companyId;
    private final UUID assetTypeId;
    private final UUID subAssetTypeId;
    private final UUID ownershipTypeId;
    private final UUID assetStatusId;
    private final UUID locationId;
    private final UUID ownerId;
    private final AssetCode code;
    private final String name;
    private final String description;
    private final LocalDate registrationDate;
    private final Money value;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isActive;
    private final Long version; // 1. Nuevo atributo de dominio

    private Asset(AssetId id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                   UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                   AssetCode code, String name, String description, LocalDate registrationDate,
                   Money value, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive, Long version) {
        if (companyId == null || assetTypeId == null || ownershipTypeId == null || assetStatusId == null
                || locationId == null || name == null || name.isBlank() || registrationDate == null) {
            throw new InvalidAssetException("Asset required fields are missing");
        }
        this.id = id == null ? AssetId.generate() : id;
        this.companyId = companyId;
        this.assetTypeId = assetTypeId;
        this.subAssetTypeId = subAssetTypeId;
        this.ownershipTypeId = ownershipTypeId;
        this.assetStatusId = assetStatusId;
        this.locationId = locationId;
        this.ownerId = ownerId;
        this.code = code;
        this.name = name.trim();
        this.description = description;
        this.registrationDate = registrationDate;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive != null ? isActive : true;
        this.version = version;
    }

    public static Asset create(UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                               UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                               String code, String name, String description, LocalDate registrationDate) {
        // La versión nace en null. JPA sabrá que debe hacer un INSERT.
        return new Asset(null, companyId, assetTypeId, subAssetTypeId, ownershipTypeId, assetStatusId,
                locationId, ownerId, AssetCode.of(code), name, description, registrationDate, null, null, null, true, null);
    }

    public static Asset restore(AssetId id, UUID companyId, UUID assetTypeId, UUID subAssetTypeId,
                                UUID ownershipTypeId, UUID assetStatusId, UUID locationId, UUID ownerId,
                                AssetCode code, String name, String description, LocalDate registrationDate,
                                Money value, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive, Long version) {
        return new Asset(id, companyId, assetTypeId, subAssetTypeId, ownershipTypeId, assetStatusId,
                locationId, ownerId, code, name, description, registrationDate, value, createdAt, updatedAt, isActive, version);
    }

    public Asset update(UUID companyId, UUID assetTypeId, UUID subAssetTypeId, UUID ownershipTypeId,
                        UUID assetStatusId, UUID locationId, UUID ownerId, String code, String name,
                        String description, LocalDate registrationDate) {
        // Se mantiene la versión actual, JPA la incrementará al guardar
        return new Asset(id, companyId, assetTypeId, subAssetTypeId, ownershipTypeId, assetStatusId,
                locationId, ownerId, AssetCode.of(code), name, description, registrationDate, value,
                createdAt, LocalDateTime.now(), this.isActive, this.version);
    }

    public Asset withActiveState(boolean newActiveState) {
        return new Asset(id, companyId, assetTypeId, subAssetTypeId, ownershipTypeId, assetStatusId,
                locationId, ownerId, code, name, description, registrationDate, value,
                createdAt, LocalDateTime.now(), newActiveState, this.version);
    }

    public AssetId getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public UUID getAssetTypeId() { return assetTypeId; }
    public UUID getSubAssetTypeId() { return subAssetTypeId; }
    public UUID getOwnershipTypeId() { return ownershipTypeId; }
    public UUID getAssetStatusId() { return assetStatusId; }
    public UUID getLocationId() { return locationId; }
    public UUID getOwnerId() { return ownerId; }
    public AssetCode getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public Money getValue() { return value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Boolean getIsActive() { return isActive; }
    public Long getVersion() { return version; }
}