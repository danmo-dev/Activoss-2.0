package com.datacenter.asset.application.service.owner;

import com.datacenter.asset.domain.models.owner.OwnershipType;
import com.datacenter.asset.domain.ports.out.owner.OwnershipTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnershipTypeService {

    private final OwnershipTypeRepositoryPort repositoryPort;

    public OwnershipType create(String code, String name) {
        if (repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe un tipo de propiedad con el código: " + code);
        }
        OwnershipType ownershipType = new OwnershipType(null, code, name);
        return repositoryPort.save(ownershipType);
    }

    public OwnershipType findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de propiedad no encontrado con id: " + id));
    }

    public List<OwnershipType> findAll() {
        return repositoryPort.findAll();
    }

    public OwnershipType update(UUID id, String code, String name) {
        OwnershipType existing = findById(id);

        if (!existing.getCode().equals(code) && repositoryPort.existsByCode(code)) {
            throw new IllegalArgumentException("Ya existe otro tipo de propiedad con el código: " + code);
        }

        existing.setCode(code);
        existing.setName(name);

        return repositoryPort.save(existing);
    }

    public void delete(UUID id) {
        OwnershipType existing = findById(id);
        System.out.println("Eliminando tipo de propiedad con código: " + existing.getCode());
        repositoryPort.deleteById(id);
    }
}