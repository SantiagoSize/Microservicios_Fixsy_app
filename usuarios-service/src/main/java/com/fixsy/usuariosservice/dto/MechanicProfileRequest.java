package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para crear perfil de mecánico")
public class MechanicProfileRequest {
    @Schema(description = "ID del usuario", example = "10")
    @NotNull
    private Long userId;

    @Schema(description = "Especialidad", example = "Transmisiones")
    private String specialty;

    @Schema(description = "Años de experiencia", example = "5")
    private Integer experienceYears;

    @Schema(description = "Precio por hora", example = "40")
    private Integer pricePerHour;

    @Schema(description = "Disponibilidad", example = "true")
    private Boolean isAvailable;

    @Schema(description = "Rating promedio", example = "4.7")
    private Float averageRating;
}
