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

    public OwnerService(
            OwnerRepositoryPort repositoryPort
    ) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Owner createOwner(
            UUID companyId,
            UUID ownershipTypeId
    ) {

        Owner owner = new Owner(
                null,
                companyId,
                ownershipTypeId
        );

        return repositoryPort.save(owner);
    }

    @Override
    public List<Owner> getOwnersByCompany(
            UUID companyId
    ) {
        return repositoryPort.findByCompanyId(companyId);
    }
}
