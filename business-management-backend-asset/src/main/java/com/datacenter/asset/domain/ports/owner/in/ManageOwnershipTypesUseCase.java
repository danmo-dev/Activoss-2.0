package com.datacenter.asset.domain.ports.owner.in;

import java.util.List;

import com.datacenter.asset.domain.owner.OwnershipType;

public interface ManageOwnershipTypesUseCase {

    OwnershipType createOwnershipType(String code, String name);

    List<OwnershipType> getAllOwnershipTypes();
}