package com.datacenter.asset.infrastructure.adapters.company.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.company.out.persistence.entity.CompanyEntity;

import java.util.UUID;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, UUID> {

    boolean existsByTaxId(String taxId);

}
