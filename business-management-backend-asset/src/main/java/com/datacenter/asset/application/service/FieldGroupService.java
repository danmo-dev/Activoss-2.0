package com.datacenter.asset.application.service;

import com.datacenter.asset.domain.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.in.FieldGroupUseCase;
import com.datacenter.asset.domain.ports.out.FieldGroupRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldGroupService implements FieldGroupUseCase {

    private final FieldGroupRepositoryPort repository;

    public FieldGroupService(FieldGroupRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public FieldGroup create(FieldGroup fieldGroup) {
        fieldGroup.setActive(true);
        return repository.save(fieldGroup);
    }

    @Override
    public List<FieldGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public FieldGroup findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field group not found: " + id));
    }

    @Override
    public List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    @Override
    public FieldGroup update(UUID id, String name, Integer displayOrder, UUID subAssetTypeId) {
        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field group not found: " + id));

        fieldGroup.setName(name);
        fieldGroup.setDisplayOrder(displayOrder);
        fieldGroup.setSubAssetTypeId(subAssetTypeId);

        return repository.update(fieldGroup);
    }

    @Override
    public void activate(UUID id) {
        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field group not found: " + id));
        fieldGroup.setActive(true);
        repository.update(fieldGroup);
    }

    @Override
    public void deactivate(UUID id) {
        FieldGroup fieldGroup = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field group not found: " + id));
        fieldGroup.setActive(false);
        repository.update(fieldGroup);
    }
}
