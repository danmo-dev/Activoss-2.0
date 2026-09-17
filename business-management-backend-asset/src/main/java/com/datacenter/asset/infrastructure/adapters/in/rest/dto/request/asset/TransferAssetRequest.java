package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferAssetRequest {
    @NotNull(message = "El ID de quien entrega es obligatorio")
    private UUID deliveredById; // Quién entrega
    
    @NotNull(message = "El ID del nuevo responsable es obligatorio")
    private UUID newAssigneeId; // Quién recibe
    
    private String transferReason;
}