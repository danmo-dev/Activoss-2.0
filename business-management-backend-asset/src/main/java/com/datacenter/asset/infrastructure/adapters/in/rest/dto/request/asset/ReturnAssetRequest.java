package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class ReturnAssetRequest {
    @NotNull(message = "El ID de quien devuelve el activo es obligatorio")
    private UUID returnedById;
    
    private String returnReason;
}