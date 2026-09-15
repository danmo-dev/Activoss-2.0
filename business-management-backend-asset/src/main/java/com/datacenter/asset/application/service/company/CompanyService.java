package com.datacenter.asset.application.service.company;

import com.datacenter.asset.domain.models.company.Company;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepositoryPort companyRepositoryPort;

    public Company create(Company company) {
        if (companyRepositoryPort.existsByTaxId(company.getTaxId())) {
            throw new IllegalArgumentException("Ya existe una empresa con este NIT/TaxID.");
        }
        company.setActive(true);
        company.setCreatedAt(LocalDateTime.now());
        return companyRepositoryPort.save(company);
    }

    public Company findById(UUID id) {
        return companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    public List<Company> findAll() {
        return companyRepositoryPort.findAll();
    }

    public Company update(UUID id, Company updatedData) {
        Company existing = companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        if (!existing.getTaxId().equals(updatedData.getTaxId()) &&
            companyRepositoryPort.existsByTaxId(updatedData.getTaxId())) {
            throw new IllegalArgumentException("Ya existe otra empresa con este NIT/TaxID.");
        }

        existing.setTaxId(updatedData.getTaxId());
        existing.setName(updatedData.getName());
        existing.setCompanyType(updatedData.getCompanyType());

        return companyRepositoryPort.save(existing);
    }

    public Company activate(UUID id) {
        Company company = findById(id);
        company.setActive(true);
        return companyRepositoryPort.save(company);
    }

    public Company deactivate(UUID id) {
        Company company = findById(id);
        company.setActive(false);
        return companyRepositoryPort.save(company);
    }

    public void delete(UUID id) {
        Company existing = findById(id);
        System.out.println("Eliminando empresa con NIT: " + existing.getTaxId());
        companyRepositoryPort.deleteById(id);
    }
}
