package com.datacenter.asset.infrastructure.adapters.asset.in.rest.dto.request;

import lombok.Data;

@Data
public class ChangeStateRequest {
    private String reason;
    private String executedBy; // En el futuro esto vendrá del token de seguridad (Spring Security)
}