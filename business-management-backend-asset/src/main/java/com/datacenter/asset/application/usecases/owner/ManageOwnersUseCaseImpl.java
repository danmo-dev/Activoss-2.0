package com.datacenter.asset.application.usecases.owner;

import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.owner.Owner;
import com.datacenter.asset.domain.ports.in.owner.ManageOwnersUseCase;
import com.datacenter.asset.domain.ports.out.owner.OwnerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageOwnersUseCaseImpl implements ManageOwnersUseCase {

    private final OwnerRepositoryPort repositoryPort;

    @Override
    @Transactional
    public Owner createOwner(UUID companyId, UUID ownershipTypeId) {
        Owner owner = new Owner(null, companyId, ownershipTypeId);
        return repositoryPort.save(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> getAllOwners() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> getOwnersByCompany(UUID companyId) {
        return repositoryPort.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public Owner update(UUID id, UUID companyId, UUID ownershipTypeId) {
        Owner existing = getById(id);
        existing.setCompanyId(companyId);
        existing.setOwnershipTypeId(ownershipTypeId);
        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Owner existing = getById(id);
        repositoryPort.deleteById(existing.getId());
    }
}