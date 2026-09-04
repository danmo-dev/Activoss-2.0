package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request;

import java.util.UUID;

public class CreateFieldDefinitionRequest {

    private UUID subAssetTypeId;
    private UUID fieldGroupId;
    private String name;
    private String label;
    private String fieldType;
    private Boolean required;
    private Boolean visible;
    private Boolean editable;
    private Boolean unique;
    private Integer maxLength;
    private Integer displayOrder;
    private String defaultValue;

    public CreateFieldDefinitionRequest() {
    }

    public UUID getSubAssetTypeId() {
        return subAssetTypeId;
    }

    public void setSubAssetTypeId(UUID subAssetTypeId) {
        this.subAssetTypeId = subAssetTypeId;
    }

    public UUID getFieldGroupId() {
        return fieldGroupId;
    }

    public void setFieldGroupId(UUID fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}