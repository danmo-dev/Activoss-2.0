package com.datacenter.asset.domain.models.assignment;

public enum AssignmentState {
    PENDING,        // Esperando aceptación
    ACCEPTED,       // Aceptada por el colaborador
    REJECTED,       // Rechazada
    TRANSFERRED,    // Transferida a otro colaborador
    RETURNED,       // Devuelta al almacén
    FINISHED  
}