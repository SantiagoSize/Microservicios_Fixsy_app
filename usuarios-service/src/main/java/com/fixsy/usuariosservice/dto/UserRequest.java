package com.fixsy.usuariosservice.dto;

import com.fixsy.usuariosservice.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para registrar usuario")
public class UserRequest {
    @Schema(description = "Nombre completo", example = "Juan Perez")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String name;

    @Schema(description = "Correo electrónico", example = "usuario@example.com")
    @Email
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String email;

    @Schema(description = "Contraseña en texto plano (hash pendiente)", example = "password123")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String password;

    @Schema(description = "Teléfono de contacto", example = "+1 555 0100")
    @jakarta.validation.constraints.Size(max = 50)
    private String phone;

    @Schema(description = "Rol del usuario", example = "CLIENT", allowableValues = {"CLIENT", "MECHANIC", "ADMIN"})
    @NotNull
    private Role role;
}
