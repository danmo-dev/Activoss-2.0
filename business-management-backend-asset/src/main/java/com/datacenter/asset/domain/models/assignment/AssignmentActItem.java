package com.datacenter.asset.domain.models.assignment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentActItem {

    private String assetCode;
    private String assetName;

    private String locationCode;

    private String companyTaxId;
    private String companyName;

    private String assetSerial;
    private String assetMarca;
    private String assetModelo;
    private String assetProcesador;
    private String assetEstado;
    private String assetPlaca;
    private String assetAtributo;
}