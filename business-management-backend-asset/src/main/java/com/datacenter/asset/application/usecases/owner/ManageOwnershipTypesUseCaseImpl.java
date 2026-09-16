package com.datacenter.asset.application.usecases.owner;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.owner.OwnershipType;
import com.datacenter.asset.domain.ports.in.owner.ManageOwnershipTypesUseCase;
import com.datacenter.asset.domain.ports.out.owner.OwnershipTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageOwnershipTypesUseCaseImpl implements ManageOwnershipTypesUseCase {

    private final OwnershipTypeRepositoryPort repositoryPort;

    @Override
    @Transactional
    public OwnershipType createOwnershipType(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe un tipo de propiedad con el código: " + code);
        }
        OwnershipType ownershipType = new OwnershipType(null, code, name);
        return repositoryPort.save(ownershipType);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnershipType getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de propiedad no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnershipType> getAllOwnershipTypes() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional
    public OwnershipType update(UUID id, String code, String name) {
        OwnershipType existing = getById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new BusinessException("Ya existe otro tipo de propiedad con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);

        return repositoryPort.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        OwnershipType existing = getById(id);
        repositoryPort.deleteById(existing.getId());
    }
}