package com.datacenter.asset.infrastructure.adapters.in.rest.controller;

import com.datacenter.asset.domain.company.Company;
import com.datacenter.asset.domain.ports.in.ManageCompanyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final ManageCompanyUseCase manageCompanyUseCase;

    public CompanyController(ManageCompanyUseCase manageCompanyUseCase) {
        this.manageCompanyUseCase = manageCompanyUseCase;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return ResponseEntity.ok(manageCompanyUseCase.createCompany(company));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(manageCompanyUseCase.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID id) {
        return ResponseEntity.ok(manageCompanyUseCase.getCompanyById(id));
    }
}