package com.fixsy.catalogoservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para crear servicio")
public class ServiceCreateRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Nombre del servicio", example = "Mantenimiento básico")
    private String name;

    @NotBlank
    @Schema(description = "Descripción del servicio", example = "Incluye cambio de aceite y filtros")
    private String description;

    @NotNull
    @Min(0)
    @Schema(description = "Precio base", example = "50000")
    private Integer basePrice;

    @NotNull
    @Min(1)
    @Schema(description = "Tiempo estimado en minutos", example = "90")
    private Integer estimatedTimeMinutes;
}
