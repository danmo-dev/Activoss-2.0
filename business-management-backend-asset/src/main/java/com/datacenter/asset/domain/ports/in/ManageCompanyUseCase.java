package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.company.Company;
import java.util.List;
import java.util.UUID;

public interface ManageCompanyUseCase {
    Company createCompany(Company company);
    Company getCompanyById(UUID id);
    List<Company> getAllCompanies();
}