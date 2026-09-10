package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.OwnershipTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OwnershipTypeJpaRepository extends JpaRepository<OwnershipTypeEntity, UUID> {

}
