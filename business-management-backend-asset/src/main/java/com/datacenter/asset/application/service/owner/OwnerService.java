package com.datacenter.asset.application.service.owner;

import com.datacenter.asset.domain.models.owner.Owner;
import com.datacenter.asset.domain.ports.out.owner.OwnerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepositoryPort repositoryPort;

    public Owner create(UUID companyId, UUID ownershipTypeId) {
        Owner owner = new Owner(null, companyId, ownershipTypeId);
        return repositoryPort.save(owner);
    }

    public Owner findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
    }

    public List<Owner> findAll() {
        return repositoryPort.findAll();
    }

    public List<Owner> findByCompany(UUID companyId) {
        return repositoryPort.findByCompanyId(companyId);
    }

    public Owner update(UUID id, UUID companyId, UUID ownershipTypeId) {
        Owner existing = findById(id);
        existing.setCompanyId(companyId);
        existing.setOwnershipTypeId(ownershipTypeId);
        return repositoryPort.save(existing);
    }

    public void delete(UUID id) {
        Owner existing = findById(id);
        repositoryPort.deleteById(id);
    }
}