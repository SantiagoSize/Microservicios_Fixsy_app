package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Payload de login")
public class LoginRequest {
    @Schema(description = "Correo electrónico", example = "usuario@example.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Contraseña en texto plano", example = "password123")
    @NotBlank
    private String password;
}
