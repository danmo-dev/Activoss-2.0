package com.datacenter.asset.infrastructure.adapters.in.rest.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    private boolean success;
    private String message;
    private String errorCode; // Ej: "ERR_NOT_FOUND", "ERR_VALIDATION"
    private T data; // Aquí irá tu DTO cuando sea exitoso, o null cuando haya error
    
    // Método de utilidad para crear respuestas exitosas rápidamente
    public static <T> GeneralResponse<T> success(T data, String message) {
        return GeneralResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
}
