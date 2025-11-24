package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de usuario")
public class UserResponse {
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String name;

    @Schema(description = "Correo del usuario", example = "usuario@example.com")
    private String email;

    @Schema(description = "Teléfono del usuario", example = "+1 555 0100")
    private String phone;

    @Schema(description = "Rol del usuario", example = "CLIENT")
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
