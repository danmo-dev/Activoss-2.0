package com.datacenter.asset.domain.ports.out.company;

import com.datacenter.asset.domain.models.company.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryPort {
    Company save(Company company);
    Optional<Company> findById(UUID id);
    List<Company> findAll();
    boolean existsByTaxId(String taxId);
    void deleteById(UUID id);
}