package com.datacenter.asset.infrastructure.adapters.company.out.persistence.repository.adapter;

import com.datacenter.asset.domain.company.Company;
import com.datacenter.asset.domain.ports.company.out.CompanyRepositoryPort;
import com.datacenter.asset.infrastructure.adapters.company.out.persistence.entity.CompanyEntity;
import com.datacenter.asset.infrastructure.adapters.company.out.persistence.repository.jpa.CompanyJpaRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CompanyRepositoryAdapter implements CompanyRepositoryPort {
    private final CompanyJpaRepository repository;

    public CompanyRepositoryAdapter(CompanyJpaRepository repository) { this.repository = repository; }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setTaxId(company.getTaxId());
        entity.setName(company.getName());
        entity.setCompanyType(company.getCompanyType());
        entity.setActive(company.isActive());
        entity.setCreatedAt(company.getCreatedAt());
        return mapToDomain(repository.save(entity));
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Company> findAll() {
        return repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByTaxId(String taxId) {
        return repository.existsByTaxId(taxId);
    }

    private Company mapToDomain(CompanyEntity entity) {
        return new Company(entity.getId(), entity.getTaxId(), entity.getName(), entity.getCompanyType(), entity.isActive(), entity.getCreatedAt());
    }
}