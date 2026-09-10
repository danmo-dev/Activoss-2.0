package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, UUID> {
    List<OwnerEntity> findByCompanyId(UUID companyId);
}
