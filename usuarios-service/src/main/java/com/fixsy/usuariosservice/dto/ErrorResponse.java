package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Estructura de error estandarizada")
public class ErrorResponse {

    @Schema(description = "Fecha y hora del error")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP", example = "404")
    private int status;

    @Schema(description = "Razón del estado", example = "Not Found")
    private String error;

    @Schema(description = "Mensaje detallado", example = "Usuario no encontrado")
    private String message;

    @Schema(description = "Path de la solicitud", example = "/users/1")
    private String path;
}
