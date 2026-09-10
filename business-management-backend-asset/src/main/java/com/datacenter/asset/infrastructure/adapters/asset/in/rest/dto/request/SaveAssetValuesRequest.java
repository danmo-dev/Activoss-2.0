package com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class SaveAssetValuesRequest {
    private List<ValueItem> values;

    @Data
    public static class ValueItem {
        private UUID fieldDefinitionId;
        private String value;
    }
}