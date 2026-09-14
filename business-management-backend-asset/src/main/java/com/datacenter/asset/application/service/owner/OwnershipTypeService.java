package com.datacenter.asset.application.service.owner;

import com.datacenter.asset.domain.owner.OwnershipType;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnershipTypesUseCase;
import com.datacenter.asset.domain.ports.owner.out.OwnershipTypeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OwnershipTypeService implements ManageOwnershipTypesUseCase {

    private final OwnershipTypeRepositoryPort repositoryPort;

    public OwnershipTypeService(OwnershipTypeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public OwnershipType createOwnershipType(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un tipo de propiedad con el código: " + code);
        }

        OwnershipType ownershipType = new OwnershipType(
                null,
                code,
                name
        );

        return repositoryPort.save(ownershipType);
    }

    @Override
    public OwnershipType getById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de propiedad no encontrado con id: " + id));
    }

    @Override
    public List<OwnershipType> getAllOwnershipTypes() {
        return repositoryPort.findAll();
    }

    @Override
    public OwnershipType update(UUID id, String code, String name) {
        OwnershipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de propiedad no encontrado con id: " + id));

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro tipo de propiedad con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);

        return repositoryPort.save(existing);
    }

    @Override
    public void delete(UUID id) {
        OwnershipType existing = repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de propiedad no encontrado con id: " + id));
                System.out.println("Eliminando estado con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}