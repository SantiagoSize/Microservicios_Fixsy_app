package com.fixsy.usuariosservice.dto;

import com.fixsy.usuariosservice.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Payload para actualizar usuario")
public class UserUpdateRequest {
    @Schema(description = "Nombre completo", example = "Juan Perez")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String name;

    @Schema(description = "Correo electrónico", example = "usuario@example.com")
    @Email
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+1 555 0100")
    @jakarta.validation.constraints.Size(max = 50)
    private String phone;

    @Schema(description = "Rol del usuario", example = "ADMIN", allowableValues = {"CLIENT", "MECHANIC", "ADMIN"})
    private Role role;
}
