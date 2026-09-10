package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.domain.ports.asset.in.ManageAssetStatusesUseCase;
import com.datacenter.asset.domain.ports.asset.out.AssetStatusRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetStatusService implements ManageAssetStatusesUseCase {

    private final AssetStatusRepositoryPort repositoryPort;

    public AssetStatusService(
            AssetStatusRepositoryPort repositoryPort
    ) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public AssetStatus createAssetStatus(
            String code,
            String name
    ) {

        AssetStatus assetStatus = new AssetStatus(
                null,
                code,
                name
        );

        return repositoryPort.save(assetStatus);
    }

    @Override
    public List<AssetStatus> getAllAssetStatuses() {
        return repositoryPort.findAll();
    }
}