package com.datacenter.asset.application.service.owner;

import com.datacenter.asset.domain.owner.Owner;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnersUseCase;
import com.datacenter.asset.domain.ports.owner.out.OwnerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OwnerService implements ManageOwnersUseCase {

    private final OwnerRepositoryPort repositoryPort;

    public OwnerService(OwnerRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Owner createOwner(UUID companyId, UUID ownershipTypeId) {
        Owner owner = new Owner(
                null,
                companyId,
                ownershipTypeId
        );
        return repositoryPort.save(owner);
    }

    @Override
    public Owner getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
    }

    @Override
    public List<Owner> getAllOwners() {
        return repositoryPort.findAll();
    }

    @Override
    public List<Owner> getOwnersByCompany(UUID companyId) {
        return repositoryPort.findByCompanyId(companyId);
    }

    @Override
    public Owner update(UUID id, UUID companyId, UUID ownershipTypeId) {
        Owner existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));

        existing.setCompanyId(companyId);
        existing.setOwnershipTypeId(ownershipTypeId);

        return repositoryPort.save(existing);
    }

    @Override
    public void delete(UUID id) {
        Owner existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
        repositoryPort.deleteById(id);
    }
}