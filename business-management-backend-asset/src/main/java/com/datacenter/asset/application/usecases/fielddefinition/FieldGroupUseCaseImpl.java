package com.datacenter.asset.application.usecases.fielddefinition;

import com.datacenter.asset.domain.exception.ResourceNotFoundException;
import com.datacenter.asset.domain.models.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.in.fielddefinition.FieldGroupUseCase;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldGroupRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FieldGroupUseCaseImpl implements FieldGroupUseCase {

    private final FieldGroupRepositoryPort repository;

    @Override
    @Transactional
    public FieldGroup create(FieldGroup fieldGroup) {
        fieldGroup.setActive(true);
        return repository.save(fieldGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldGroup> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FieldGroup findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo de campos no encontrado: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    @Transactional
    public FieldGroup update(UUID id, String name, Integer displayOrder, UUID subAssetTypeId) {
        FieldGroup fieldGroup = findById(id);
        fieldGroup.setName(name);
        fieldGroup.setDisplayOrder(displayOrder);
        fieldGroup.setSubAssetTypeId(subAssetTypeId);
        return repository.update(fieldGroup);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        FieldGroup fieldGroup = findById(id);
        fieldGroup.setActive(true);
        repository.update(fieldGroup);
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        FieldGroup fieldGroup = findById(id);
        fieldGroup.setActive(false);
        repository.update(fieldGroup);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        FieldGroup fieldGroup = findById(id);
        repository.deleteById(fieldGroup.getId());
    }
}