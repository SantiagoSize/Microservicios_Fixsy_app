package com.fixsy.solicitudesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para crear solicitud de servicio")
public class ServiceRequestCreateRequest {

    @NotNull
    @Schema(description = "ID del cliente", example = "1")
    private Long clientId;

    @Schema(description = "ID del mecánico", example = "5")
    private Long mechanicId;

    @Schema(description = "ID del vehículo", example = "12")
    private Long vehicleId;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Tipo de servicio", example = "Mantenimiento preventivo")
    private String serviceType;

    @Schema(description = "Descripción de la solicitud", example = "Cambio de aceite y filtros")
    private String description;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Ubicación", example = "Calle 123 #45-67")
    private String location;

    @Schema(description = "JSON de URLs de imágenes", example = "[\"https://.../1.jpg\"]")
    private String images;

    @Size(max = 255)
    @Schema(description = "Costo estimado", example = "150.00")
    private String estimatedCost;

    @Schema(description = "Notas adicionales", example = "Usar aceite sintético")
    private String notes;
}
