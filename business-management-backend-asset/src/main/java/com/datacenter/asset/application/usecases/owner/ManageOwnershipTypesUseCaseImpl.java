package com.datacenter.asset.application.usecases.owner;

import com.datacenter.asset.domain.models.owner.OwnershipType;
import com.datacenter.asset.domain.ports.in.owner.ManageOwnershipTypesUseCase;
import com.datacenter.asset.application.service.owner.OwnershipTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageOwnershipTypesUseCaseImpl implements ManageOwnershipTypesUseCase {

    private final OwnershipTypeService ownershipTypeService;

    @Override
    @Transactional
    public OwnershipType createOwnershipType(String code, String name) {
        return ownershipTypeService.create(code, name);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnershipType getById(UUID id) {
        return ownershipTypeService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnershipType> getAllOwnershipTypes() {
        return ownershipTypeService.findAll();
    }

    @Override
    @Transactional
    public OwnershipType update(UUID id, String code, String name) {
        return ownershipTypeService.update(id, code, name);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ownershipTypeService.delete(id);
    }
}