package com.datacenter.asset.infrastructure.exceptions;

import com.datacenter.asset.domain.exception.AssetNotFoundException;
import com.datacenter.asset.domain.exception.DuplicateAssetCodeException;
import com.datacenter.asset.domain.exception.InvalidAssetException;
import com.datacenter.asset.infrastructure.adapters.in.rest.utils.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de error cuando un activo no existe
    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<GeneralResponse<Void>> handleAssetNotFound(AssetNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        
        GeneralResponse<Void> response = GeneralResponse.<Void>builder()
                .success(false)
                .errorCode("ERR_NOT_FOUND")
                .message(ex.getMessage())
                .build();
                
        // SIEMPRE devuelve 200 OK, como pidió tu líder
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 2. Manejo de error de reglas de negocio (ej. AssetCode inválido)
    @ExceptionHandler({InvalidAssetException.class, IllegalArgumentException.class})
    public ResponseEntity<GeneralResponse<Void>> handleValidationExceptions(RuntimeException ex) {
        log.warn("Error de validación de negocio: {}", ex.getMessage());
        
        GeneralResponse<Void> response = GeneralResponse.<Void>builder()
                .success(false)
                .errorCode("ERR_VALIDATION")
                .message(ex.getMessage())
                .build();
                
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3. Manejo de error por códigos duplicados
    @ExceptionHandler(DuplicateAssetCodeException.class)
    public ResponseEntity<GeneralResponse<Void>> handleDuplicateCode(DuplicateAssetCodeException ex) {
        log.warn("Conflicto de datos: {}", ex.getMessage());
        
        GeneralResponse<Void> response = GeneralResponse.<Void>builder()
                .success(false)
                .errorCode("ERR_DUPLICATE_CODE")
                .message(ex.getMessage())
                .build();
                
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4. FALLBACK: Cualquier otra excepción no controlada (NullPointer, Base de datos caída, etc)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<Void>> handleGenericException(Exception ex) {
        log.error("Error crítico inesperado: ", ex);
        
        GeneralResponse<Void> response = GeneralResponse.<Void>builder()
                .success(false)
                .errorCode("ERR_INTERNAL_SERVER")
                .message("Ocurrió un error inesperado en el servidor. Contacte a soporte.")
                .build();
                
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}