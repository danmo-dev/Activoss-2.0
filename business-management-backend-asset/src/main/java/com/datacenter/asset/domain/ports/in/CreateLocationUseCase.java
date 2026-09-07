package com.datacenter.asset.domain.ports.in;

import com.datacenter.asset.domain.location.Location;

public interface CreateLocationUseCase {
    Location createLocation(Location location);
}