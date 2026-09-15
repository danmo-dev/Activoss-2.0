package com.datacenter.asset.application.usecases.company;

import com.datacenter.asset.domain.models.company.Company;
import com.datacenter.asset.domain.ports.in.company.ManageCompanyUseCase;
import com.datacenter.asset.application.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageCompanyUseCaseImpl implements ManageCompanyUseCase {

    private final CompanyService companyService;

    @Override
    @Transactional
    public Company createCompany(Company company) {
        return companyService.create(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(UUID id) {
        return companyService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @Override
    @Transactional
    public Company update(UUID id, Company company) {
        return companyService.update(id, company);
    }

    @Override
    @Transactional
    public Company activate(UUID id) {
        return companyService.activate(id);
    }

    @Override
    @Transactional
    public Company deactivate(UUID id) {
        return companyService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        companyService.delete(id);
    }
}