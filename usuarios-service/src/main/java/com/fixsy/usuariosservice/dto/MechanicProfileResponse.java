package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de perfil de mecánico")
public class MechanicProfileResponse {
    @Schema(description = "ID del usuario/mecánico", example = "10")
    private Long userId;

    @Schema(description = "Nombre del mecánico", example = "María Gomez")
    private String name;

    @Schema(description = "Correo del mecánico", example = "maria@example.com")
    private String email;

    @Schema(description = "Teléfono", example = "+1 555 0101")
    private String phone;

    private String specialty;
    private Integer experienceYears;
    private Integer pricePerHour;
    private Boolean isAvailable;
    private Float averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
