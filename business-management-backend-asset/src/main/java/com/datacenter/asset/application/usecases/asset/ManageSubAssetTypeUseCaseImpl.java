package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.models.configuration.SubAssetType;
import com.datacenter.asset.domain.ports.in.subasset.ManageSubAssetTypeUseCase;
import com.datacenter.asset.application.service.asset.SubAssetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManageSubAssetTypeUseCaseImpl implements ManageSubAssetTypeUseCase {

    private final SubAssetTypeService subAssetTypeService;

    @Override
    @Transactional
    public SubAssetType create(SubAssetType subAssetType) {
        return subAssetTypeService.create(subAssetType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubAssetType> findAll() {
        return subAssetTypeService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubAssetType> findByAssetTypeId(UUID assetTypeId) {
        return subAssetTypeService.findByAssetTypeId(assetTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public SubAssetType findById(UUID id) {
        return subAssetTypeService.findById(id);
    }

    @Override
    @Transactional
    public SubAssetType update(UUID id, String code, String name, String description) {
        return subAssetTypeService.update(id, code, name, description);
    }

    @Override
    @Transactional
    public SubAssetType activate(UUID id) {
        return subAssetTypeService.activate(id);
    }

    @Override
    @Transactional
    public SubAssetType desactivate(UUID id) {
        return subAssetTypeService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        subAssetTypeService.delete(id);
    }
}