package com.datacenter.asset.domain.ports.owner.in;

import java.util.List;
import java.util.UUID;

import com.datacenter.asset.domain.owner.Owner;

public interface ManageOwnersUseCase {

    Owner createOwner(
            UUID companyId,
            UUID ownershipTypeId
    );

    List<Owner> getOwnersByCompany(UUID companyId);
}