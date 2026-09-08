package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.company.Company;
import com.datacenter.asset.domain.ports.in.ManageCompanyUseCase;
import com.datacenter.asset.domain.ports.out.CompanyRepositoryPort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService implements ManageCompanyUseCase {
    private final CompanyRepositoryPort companyRepositoryPort;

    public CompanyService(CompanyRepositoryPort companyRepositoryPort) {
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    public Company createCompany(Company company) {
        if (companyRepositoryPort.existsByTaxId(company.getTaxId())) {
            throw new IllegalArgumentException("Ya existe una empresa con este NIT/TaxID.");
        }
        company.setActive(true);
        company.setCreatedAt(LocalDateTime.now());
        return companyRepositoryPort.save(company);
    }

    @Override
    public Company getCompanyById(UUID id) {
        return companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepositoryPort.findAll();
    }
}
