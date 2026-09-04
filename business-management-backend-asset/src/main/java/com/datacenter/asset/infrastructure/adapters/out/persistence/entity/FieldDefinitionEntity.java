package com.datacenter.asset.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "field_definitions")
@Getter
@Setter
public class FieldDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sub_asset_type_id")
    private UUID subAssetTypeId;

    @Column(name = "field_group_id")
    private UUID fieldGroupId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 150)
    private String label;

    @Column(name = "field_type", nullable = false, length = 50)
    private String fieldType;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "is_visible")
    private Boolean visible;

    @Column(name = "is_editable")
    private Boolean editable;

    @Column(name = "is_unique")
    private Boolean unique;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "default_value", length = 500)
    private String defaultValue;

    @Column(name = "is_active", nullable = false)
    private Boolean active;
}