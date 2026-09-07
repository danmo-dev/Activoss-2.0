package com.datacenter.asset.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asset_history")
@Data
public class AssetHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Solución al warning deprecado
    private UUID id;

    @Column(name = "asset_id", nullable = false)
    private UUID assetId;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_type", length = 100)
    private String eventType;

    @Column(name = "executed_by", length = 100)
    private String executedBy;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}