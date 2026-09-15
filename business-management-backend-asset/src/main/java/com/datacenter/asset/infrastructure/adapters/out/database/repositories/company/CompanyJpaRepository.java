package com.datacenter.asset.infrastructure.adapters.out.database.repositories.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datacenter.asset.infrastructure.adapters.out.database.entities.company.CompanyEntity;

import java.util.UUID;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, UUID> {

    boolean existsByTaxId(String taxId);

}
