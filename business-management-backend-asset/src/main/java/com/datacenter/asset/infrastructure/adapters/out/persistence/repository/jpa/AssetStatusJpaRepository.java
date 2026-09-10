package com.datacenter.asset.infrastructure.adapters.out.persistence.repository.jpa;

import com.datacenter.asset.infrastructure.adapters.out.persistence.entity.AssetStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssetStatusJpaRepository extends JpaRepository<AssetStatusEntity, UUID> {

}
