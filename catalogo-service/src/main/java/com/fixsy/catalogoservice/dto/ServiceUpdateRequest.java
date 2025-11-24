package com.fixsy.catalogoservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para actualizar servicio")
public class ServiceUpdateRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Nombre del servicio", example = "Mantenimiento avanzado")
    private String name;

    @NotBlank
    @Schema(description = "Descripción del servicio", example = "Incluye cambio de aceite, filtros y revisión de frenos")
    private String description;

    @Min(0)
    @Schema(description = "Precio base", example = "70000")
    private Integer basePrice;

    @Min(1)
    @Schema(description = "Tiempo estimado en minutos", example = "120")
    private Integer estimatedTimeMinutes;
}
