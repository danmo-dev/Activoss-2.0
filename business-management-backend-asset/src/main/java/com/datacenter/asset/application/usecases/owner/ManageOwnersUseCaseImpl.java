package com.datacenter.asset.application.usecases.owner;

import com.datacenter.asset.domain.models.owner.Owner;
import com.datacenter.asset.domain.ports.in.owner.ManageOwnersUseCase;
import com.datacenter.asset.application.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageOwnersUseCaseImpl implements ManageOwnersUseCase {

    private final OwnerService ownerService;

    @Override
    @Transactional
    public Owner createOwner(UUID companyId, UUID ownershipTypeId) {
        return ownerService.create(companyId, ownershipTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getById(UUID id) {
        return ownerService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> getAllOwners() {
        return ownerService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> getOwnersByCompany(UUID companyId) {
        return ownerService.findByCompany(companyId);
    }

    @Override
    @Transactional
    public Owner update(UUID id, UUID companyId, UUID ownershipTypeId) {
        return ownerService.update(id, companyId, ownershipTypeId);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ownerService.delete(id);
    }
}