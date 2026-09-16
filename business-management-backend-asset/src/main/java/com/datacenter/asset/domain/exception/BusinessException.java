package com.datacenter.asset.domain.exception;

public class BusinessException extends RuntimeException {

    // Constructor estándar para mensajes de error de negocio
    public BusinessException(String message) {
        super(message);
    }

    // Por si necesitas pasar la causa original (otra excepción)
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}