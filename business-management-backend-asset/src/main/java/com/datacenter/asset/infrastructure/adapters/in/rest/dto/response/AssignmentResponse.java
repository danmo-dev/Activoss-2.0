package com.datacenter.asset.infrastructure.adapters.in.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AssignmentResponse {
    private UUID id;
    private UUID assetId;
    private UUID personId;
    private String state;
    private LocalDateTime startDate;
    private String pdfPath;
}