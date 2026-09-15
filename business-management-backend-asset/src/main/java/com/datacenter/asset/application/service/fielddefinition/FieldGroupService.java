package com.datacenter.asset.application.service.fielddefinition;

import com.datacenter.asset.domain.models.fieldgroup.FieldGroup;
import com.datacenter.asset.domain.ports.out.fielddefinition.FieldGroupRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldGroupService {

    private final FieldGroupRepositoryPort repository;

    public FieldGroup create(FieldGroup fieldGroup) {
        fieldGroup.setId(UUID.randomUUID());
        fieldGroup.setActive(true);
        return repository.save(fieldGroup);
    }

    public List<FieldGroup> findAll() {
        return repository.findAll();
    }

    public FieldGroup findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field group not found: " + id));
    }

    public List<FieldGroup> findBySubAssetTypeId(UUID subAssetTypeId) {
        return repository.findBySubAssetTypeId(subAssetTypeId);
    }

    public FieldGroup update(UUID id, String name, Integer displayOrder, UUID subAssetTypeId) {
        FieldGroup fieldGroup = findById(id);

        fieldGroup.setName(name);
        fieldGroup.setDisplayOrder(displayOrder);
        fieldGroup.setSubAssetTypeId(subAssetTypeId);

        return repository.update(fieldGroup);
    }

    public void activate(UUID id) {
        FieldGroup fieldGroup = findById(id);
        fieldGroup.setActive(true);
        repository.update(fieldGroup);
    }

    public void deactivate(UUID id) {
        FieldGroup fieldGroup = findById(id);
        fieldGroup.setActive(false);
        repository.update(fieldGroup);
    }

    public void delete(UUID id) {
        FieldGroup fieldGroup = findById(id);
        repository.deleteById(id);
    }
}