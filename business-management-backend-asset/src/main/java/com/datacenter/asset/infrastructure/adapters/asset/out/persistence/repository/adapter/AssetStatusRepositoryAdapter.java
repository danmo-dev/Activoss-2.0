package com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.adapter;

import com.datacenter.asset.domain.configuration.AssetStatus;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.entity.AssetStatusEntity;
import com.datacenter.asset.infrastructure.adapters.asset.out.persistence.repository.jpa.AssetStatusJpaRepository;
import com.datacenter.asset.domain.ports.asset.out.AssetStatusRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssetStatusRepositoryAdapter
        implements AssetStatusRepositoryPort {

    private final AssetStatusJpaRepository repository;

    public AssetStatusRepositoryAdapter(
            AssetStatusJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public AssetStatus save(AssetStatus assetStatus) {

        AssetStatusEntity entity =
                new AssetStatusEntity(
                        assetStatus.getId(),
                        assetStatus.getCode(),
                        assetStatus.getName()
                );

        entity = repository.save(entity);

        return new AssetStatus(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        );
    }

    @Override
    public List<AssetStatus> findAll() {

        return repository.findAll()
                .stream()
                .map(entity -> new AssetStatus(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName()
                ))
                .collect(Collectors.toList());
    }
}