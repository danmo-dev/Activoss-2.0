package com.datacenter.asset.infrastructure.adapters.out.database.entities.asset;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "asset_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ Hibernate 6+ soporta esto directamente
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;
}
