package com.datacenter.asset.application.service.asset;

import com.datacenter.asset.domain.asset.AssetId;
import com.datacenter.asset.domain.asset.AssetValue;
import com.datacenter.asset.domain.ports.asset.in.SaveAssetValuesUseCase;
import com.datacenter.asset.domain.ports.asset.out.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.asset.out.AssetValueRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetValueService implements SaveAssetValuesUseCase {

    private final AssetValueRepositoryPort valueRepository;
    private final AssetRepositoryPort assetRepository;

    @Override
    @Transactional
    public List<AssetValue> saveAssetValues(UUID assetId, List<AssetValue> values) {
        if (assetRepository.findById(new AssetId(assetId)).isEmpty()) {
            throw new IllegalArgumentException("Activo no encontrado para asociar valores dinámicos");
        }

        
        List<AssetValue> valuesToSave = values.stream()
                .map(v -> AssetValue.builder()
                        .id(v.getId())
                        .assetId(assetId)
                        .fieldDefinitionId(v.getFieldDefinitionId())
                        .value(v.getValue())
                        .build())
                .collect(Collectors.toList());

        return valueRepository.saveAll(valuesToSave);
    }

    @Override
    public List<AssetValue> getAssetValues(UUID assetId) {
        return valueRepository.findByAssetId(assetId);
    }
}
