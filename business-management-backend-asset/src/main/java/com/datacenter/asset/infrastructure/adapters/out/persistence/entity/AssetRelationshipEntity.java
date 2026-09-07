package com.datacenter.asset.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asset_relationships")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetRelationshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "parent_asset_id", nullable = false)
    private UUID parentAssetId;

    @Column(name = "child_asset_id", nullable = false)
    private UUID childAssetId;

    @Column(name = "relationship_type_id", nullable = false)
    private UUID relationshipTypeId;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
}
