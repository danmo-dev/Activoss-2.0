package com.datacenter.asset.application.usecases.asset;

import com.datacenter.asset.domain.exception.BusinessException;
import com.datacenter.asset.domain.models.asset.AssetId;
import com.datacenter.asset.domain.models.asset.AssetValue;
import com.datacenter.asset.domain.ports.in.asset.SaveAssetValuesUseCase;
import com.datacenter.asset.domain.ports.out.asset.AssetRepositoryPort;
import com.datacenter.asset.domain.ports.out.asset.AssetValueRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaveAssetValuesUseCaseImpl implements SaveAssetValuesUseCase {

    private final AssetValueRepositoryPort valueRepository;
    private final AssetRepositoryPort assetRepository;

    @Override
    @Transactional
    public List<AssetValue> saveAssetValues(UUID assetId, List<AssetValue> values) {
        if (assetRepository.findById(new AssetId(assetId)).isEmpty()) {
            throw new BusinessException("Activo no encontrado para asociar valores dinámicos");
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
    @Transactional(readOnly = true)
    public List<AssetValue> getAssetValues(UUID assetId) {
        return valueRepository.findByAssetId(assetId);
    }
}