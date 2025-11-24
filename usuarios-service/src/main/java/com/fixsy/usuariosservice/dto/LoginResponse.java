package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta de login")
public class LoginResponse {
    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String name;

    @Schema(description = "Correo del usuario", example = "usuario@example.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "CLIENT")
    private String role;
}
