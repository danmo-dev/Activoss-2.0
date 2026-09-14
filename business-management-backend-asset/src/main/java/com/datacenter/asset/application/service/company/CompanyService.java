package com.datacenter.asset.application.service.company;

import com.datacenter.asset.domain.company.Company;
import com.datacenter.asset.domain.ports.company.in.ManageCompanyUseCase;
import com.datacenter.asset.domain.ports.company.out.CompanyRepositoryPort;

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

    // --- NUEVO MÉTODO PARA EDITAR ---
    @Override
    public Company update(UUID id, Company updatedData) {
        Company existing = companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        // Validamos si el usuario quiere cambiar el NIT y comprobamos que el nuevo no exista ya
        if (!existing.getTaxId().equals(updatedData.getTaxId()) && 
            companyRepositoryPort.existsByTaxId(updatedData.getTaxId())) {
            throw new IllegalArgumentException("Ya existe otra empresa con este NIT/TaxID.");
        }

        // Actualizamos solo los datos permitidos
        existing.setTaxId(updatedData.getTaxId());
        existing.setName(updatedData.getName());
        existing.setCompanyType(updatedData.getCompanyType());
        
        // No actualizamos ni el ID, ni isActive, ni createdAt aquí
        return companyRepositoryPort.save(existing);
    }

    @Override
    public Company activate(UUID id) {
        Company company = companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        company.setActive(true);
        return companyRepositoryPort.save(company);
    }

    @Override
    public Company deactivate(UUID id) {
        Company company = companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        company.setActive(false);
        return companyRepositoryPort.save(company);
    }

    @Override
    public void delete(UUID id) {
        Company existing = companyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        System.out.println("Eliminando estado con código: " + existing.getTaxId());
        companyRepositoryPort.deleteById(id);
    }
}