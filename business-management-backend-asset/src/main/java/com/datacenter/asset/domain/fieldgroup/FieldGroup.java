package com.datacenter.asset.domain.fieldgroup;

import java.util.UUID;

public class FieldGroup {

    private UUID id;
    private UUID subAssetTypeId;
    private String name;
    private String description;
    private Integer displayOrder;
    private Boolean active;

    public FieldGroup() {
    }

    public FieldGroup(
            UUID id,
            UUID subAssetTypeId,
            String name,
            String description,
            Integer displayOrder,
            Boolean active
    ) {
        this.id = id;
        this.subAssetTypeId = subAssetTypeId;
        this.name = name;
        this.description = description;
        this.displayOrder = displayOrder;
        this.active = active;
    }

    public FieldGroup(
            UUID id,
            UUID subAssetTypeId,
            String name,
            Integer displayOrder,
            Boolean active
    ) {
        this.id = id;
        this.subAssetTypeId = subAssetTypeId;
        this.name = name;
        this.displayOrder = displayOrder;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSubAssetTypeId() {
        return subAssetTypeId;
    }

    public void setSubAssetTypeId(UUID subAssetTypeId) {
        this.subAssetTypeId = subAssetTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}