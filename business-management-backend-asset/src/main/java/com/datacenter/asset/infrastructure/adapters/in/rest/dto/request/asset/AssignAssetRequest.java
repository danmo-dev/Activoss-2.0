package com.datacenter.asset.infrastructure.adapters.in.rest.dto.request.asset;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AssignAssetRequest {

    @NotEmpty(message = "Debe indicar al menos un activo")
    private List<UUID> assetIds;

    @NotNull(message = "El ID de la persona es obligatorio")
    private UUID personId;

    private UUID relationshipTypeId;

    private String notes;

    @NotNull(message = "El ID de quien crea la asignación es obligatorio")
    private UUID createdById;
}