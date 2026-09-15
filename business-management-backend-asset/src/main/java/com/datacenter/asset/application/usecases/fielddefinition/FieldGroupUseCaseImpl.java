package com.datacenter.asset.application.usecases.fielddefinition;

import com.datacenter.asset.domain.models.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.in.fielddefinition.FieldGroupUseCase;
import com.datacenter.asset.application.service.fielddefinition.FieldGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FieldGroupUseCaseImpl implements FieldGroupUseCase {

    private final FieldGroupService fieldGroupService;

    @Override
    @Transactional
    public FieldGroup create(FieldGroup fieldGroup) {
        return fieldGroupService.create(fieldGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldGroup> findAll() {
        return fieldGroupService.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FieldGroup findById(UUID id) {
        return fieldGroupService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId) {
        return fieldGroupService.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    @Transactional
    public FieldGroup update(UUID id, String name, Integer displayOrder, UUID subAssetTypeId) {
        return fieldGroupService.update(id, name, displayOrder, subAssetTypeId);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        fieldGroupService.activate(id);
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        fieldGroupService.deactivate(id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        fieldGroupService.delete(id);
    }
}
