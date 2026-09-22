package com.datacenter.asset.domain.ports.in.company;

import com.datacenter.asset.domain.models.company.Company;
import java.util.List;
import java.util.UUID;

public interface ManageCompanyUseCase {
    Company createCompany(Company company);
    Company getCompanyById(UUID id);
    List<Company> getAllCompanies();
    Company update(UUID id, Company company);
    Company activate(UUID id);
    Company deactivate(UUID id);
    void delete(UUID id);
}