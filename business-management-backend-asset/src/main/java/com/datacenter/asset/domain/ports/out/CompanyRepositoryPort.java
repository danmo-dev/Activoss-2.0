package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.company.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryPort {
    Company save(Company company);
    Optional<Company> findById(UUID id);
    List<Company> findAll();
    boolean existsByTaxId(String taxId);
}