package com.datacenter.asset.application.usecases.company;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.company.Company;
import com.datacenter.asset.domain.ports.in.company.ManageCompanyUseCase;
import com.datacenter.asset.domain.ports.out.company.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageCompanyUseCaseImpl implements ManageCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    @Override
    @Transactional
    public Company createCompany(Company company) {
        if (companyRepositoryPort.existsByTaxId(company.getTaxId())) {
            throw new BusinessException("Ya existe una empresa con este NIT/TaxID.");
        }
        company.setActive(true);
        company.setCreatedAt(LocalDateTime.now());
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(UUID id) {
        return companyRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Company update(UUID id, Company company) {
        Company existing = getCompanyById(id);

        if (!existing.getTaxId().equals(company.getTaxId()) &&
            companyRepositoryPort.existsByTaxId(company.getTaxId())) {
            throw new BusinessException("Ya existe otra empresa con este NIT/TaxID.");
        }

        existing.setTaxId(company.getTaxId());
        existing.setName(company.getName());
        existing.setCompanyType(company.getCompanyType());

        return companyRepositoryPort.save(existing);
    }

    @Override
    @Transactional
    public Company activate(UUID id) {
        Company company = getCompanyById(id);
        company.setActive(true);
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional
    public Company deactivate(UUID id) {
        Company company = getCompanyById(id);
        company.setActive(false);
        return companyRepositoryPort.save(company);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Company existing = getCompanyById(id);
        companyRepositoryPort.deleteById(existing.getId());
    }
}