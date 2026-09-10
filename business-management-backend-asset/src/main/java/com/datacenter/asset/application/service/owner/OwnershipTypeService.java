package com.datacenter.asset.application.service.owner;

import com.datacenter.asset.domain.owner.OwnershipType;
import com.datacenter.asset.domain.ports.owner.in.ManageOwnershipTypesUseCase;
import com.datacenter.asset.domain.ports.owner.out.OwnershipTypeRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnershipTypeService
        implements ManageOwnershipTypesUseCase {

    private final OwnershipTypeRepositoryPort repositoryPort;

    public OwnershipTypeService(
            OwnershipTypeRepositoryPort repositoryPort
    ) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public OwnershipType createOwnershipType(
            String code,
            String name
    ) {

        OwnershipType ownershipType = new OwnershipType(
                null,
                code,
                name
        );

        return repositoryPort.save(ownershipType);
    }

    @Override
    public List<OwnershipType> getAllOwnershipTypes() {
        return repositoryPort.findAll();
    }
}
