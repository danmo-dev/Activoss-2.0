package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class AcceptAssignmentRequest {
    private String observaciones;
    
    @NotNull(message = "El ID de quien entrega es obligatorio para generar el acta")
    private UUID deliveredById; 
}