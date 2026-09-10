package com.datacenter.asset.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "owners")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OwnerEntity {
    @Id private UUID id;
    @Column(name = "company_id", nullable = false) private UUID companyId;
    @Column(name = "ownership_type_id", nullable = false) private UUID ownershipTypeId;
}
