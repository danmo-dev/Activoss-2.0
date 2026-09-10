package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "asset_statuses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetStatusEntity {
    @Id private UUID id;
    @Column(nullable = false, unique = true, length = 50) private String code;
    @Column(nullable = false, length = 100) private String name;
}